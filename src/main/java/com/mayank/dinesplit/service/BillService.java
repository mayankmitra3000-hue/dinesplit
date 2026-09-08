package com.mayank.dinesplit.service;

import com.mayank.dinesplit.dto.*;
import com.mayank.dinesplit.entity.Bill;
import com.mayank.dinesplit.entity.BillItem;
import com.mayank.dinesplit.entity.Person;
import com.mayank.dinesplit.exception.ResourceNotFoundException;
import com.mayank.dinesplit.repository.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class BillService {

    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Transactional
    public BillResponse createBill(CreateBillRequest request) {

        Bill bill = new Bill();

        bill.setName(request.name());

        BigDecimal serviceCharge =
                request.serviceCharge() == null
                        ? BigDecimal.ZERO
                        : request.serviceCharge();

        BigDecimal tax =
                request.tax() == null
                        ? BigDecimal.ZERO
                        : request.tax();

        bill.setServiceCharge(serviceCharge);
        bill.setTax(tax);

        /*
         * Create people first
         */
        Map<String, Person> personMap = new HashMap<>();

        for (String personName : request.people()) {

            String normalizedName = personName.trim();

            if (normalizedName.isEmpty()) {
                throw new IllegalArgumentException(
                        "Person name cannot be empty"
                );
            }

            if (personMap.containsKey(normalizedName.toLowerCase())) {
                throw new IllegalArgumentException(
                        "Duplicate person: " + normalizedName
                );
            }

            Person person = new Person();

            person.setName(normalizedName);
            person.setBill(bill);

            bill.getPeople().add(person);

            personMap.put(
                    normalizedName.toLowerCase(),
                    person
            );
        }

        /*
         * Create items
         */
        BigDecimal subtotal = BigDecimal.ZERO;

        for (ItemRequest itemRequest : request.items()) {

            if (itemRequest.price().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(
                        "Item price must be greater than zero"
                );
            }

            BillItem item = new BillItem();

            item.setName(itemRequest.name().trim());
            item.setPrice(itemRequest.price());
            item.setBill(bill);

            for (String participantName :
                    itemRequest.participants()) {

                Person person =
                        personMap.get(
                                participantName.trim().toLowerCase()
                        );

                if (person == null) {
                    throw new IllegalArgumentException(
                            "Person '" + participantName +
                                    "' does not exist in this bill"
                    );
                }

                item.getParticipants().add(person);
            }

            if (item.getParticipants().isEmpty()) {
                throw new IllegalArgumentException(
                        "Item must have at least one participant"
                );
            }

            bill.getItems().add(item);

            subtotal = subtotal.add(item.getPrice());
        }

        /*
         * Calculate total
         */
        BigDecimal total =
                subtotal
                        .add(serviceCharge)
                        .add(tax);

        bill.setSubtotal(
                subtotal.setScale(2, RoundingMode.HALF_UP)
        );

        bill.setTotal(
                total.setScale(2, RoundingMode.HALF_UP)
        );

        Bill savedBill = billRepository.save(bill);

        return buildResponse(savedBill);
    }

    @Transactional(readOnly = true)
    public BillResponse getBill(Long billId) {

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bill not found with id: " + billId
                        )
                );

        return buildResponse(bill);
    }

    private BillResponse buildResponse(Bill bill) {

        /*
         * Amount each person owes before tax/service charge.
         */
        Map<Long, BigDecimal> personAmounts =
                new HashMap<>();

        for (Person person : bill.getPeople()) {

            personAmounts.put(
                    person.getId(),
                    BigDecimal.ZERO
            );
        }

        /*
         * Split every item equally between its participants.
         */
        for (BillItem item : bill.getItems()) {

            int participantCount =
                    item.getParticipants().size();

            BigDecimal individualShare =
                    item.getPrice()
                            .divide(
                                    BigDecimal.valueOf(participantCount),
                                    10,
                                    RoundingMode.HALF_UP
                            );

            for (Person person : item.getParticipants()) {

                BigDecimal current =
                        personAmounts.get(person.getId());

                personAmounts.put(
                        person.getId(),
                        current.add(individualShare)
                );
            }
        }

        /*
         * Add service charge and tax proportionally
         * according to each person's food subtotal.
         */
        BigDecimal subtotal = bill.getSubtotal();

        for (Person person : bill.getPeople()) {

            BigDecimal foodAmount =
                    personAmounts.get(person.getId());

            BigDecimal serviceShare = BigDecimal.ZERO;
            BigDecimal taxShare = BigDecimal.ZERO;

            if (subtotal.compareTo(BigDecimal.ZERO) > 0) {

                serviceShare =
                        bill.getServiceCharge()
                                .multiply(foodAmount)
                                .divide(
                                        subtotal,
                                        10,
                                        RoundingMode.HALF_UP
                                );

                taxShare =
                        bill.getTax()
                                .multiply(foodAmount)
                                .divide(
                                        subtotal,
                                        10,
                                        RoundingMode.HALF_UP
                                );
            }

            BigDecimal finalAmount =
                    foodAmount
                            .add(serviceShare)
                            .add(taxShare)
                            .setScale(
                                    2,
                                    RoundingMode.HALF_UP
                            );

            personAmounts.put(
                    person.getId(),
                    finalAmount
            );
        }

        /*
         * Item response
         */
        List<BillItemResponse> itemResponses =
                bill.getItems()
                        .stream()
                        .map(item ->
                                new BillItemResponse(
                                        item.getId(),
                                        item.getName(),
                                        item.getPrice(),
                                        item.getParticipants()
                                                .stream()
                                                .map(Person::getName)
                                                .sorted()
                                                .toList()
                                )
                        )
                        .toList();

        /*
         * Person response
         */
        List<PersonResponse> personResponses =
                bill.getPeople()
                        .stream()
                        .map(person ->
                                new PersonResponse(
                                        person.getId(),
                                        person.getName(),
                                        personAmounts
                                                .get(person.getId())
                                )
                        )
                        .toList();

        return new BillResponse(
                bill.getId(),
                bill.getName(),
                bill.getSubtotal(),
                bill.getServiceCharge(),
                bill.getTax(),
                bill.getTotal(),
                itemResponses,
                personResponses
        );
    }
}