package com.github.maciejiwan.investmens_tracking;

import com.github.maciejiwan.investmens_tracking.entities.*;
import com.github.maciejiwan.investmens_tracking.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class Zadanie1 {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserModelRepository userRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private UserModel user1;
    private UserModel user2;
    private Category category1;
    private Category category2;
    private Part part1;
    private Part part2;
    private Car car1;
    private Car car2;
    private Car car3;
    private Order order1;
    private Order order2;
    private Order order3;

    @BeforeEach
    void setup() {
        // Utworzenie przykładowych danych
        user1 = userRepository.save(UserModel.builder().name("Jan").surname("Kowalski").email("kowalski@mail.po").build());
        user2 = userRepository.save(UserModel.builder().name("Anna").surname("Nowak").email("nowak@mail.com").build());

        category1 = categoryRepository.save(Category.builder().name("Kategoria 1").build());
        category2 = categoryRepository.save(Category.builder().name("Kategoria 2").build());

        part1 = partRepository.save(Part.builder().title("part 1").category(category1).build());
        part2 = partRepository.save(Part.builder().title("part 2").category(category2).build());

        List<Part> parts = new ArrayList<>();
        parts.add(part1);
        parts.add(part2);
        car1 = carRepository.save(Car.builder().brand("Marka 1").model("Model 1").releaseYear(2021).parts(parts).build());
        car2 = carRepository.save(Car.builder().brand("Marka 1").model("Model 2").releaseYear(2020).parts(parts).build());
        car3 = carRepository.save(Car.builder().brand("Marka 2").model("Model 1").releaseYear(2022).parts(parts).build());

        List<LineItem> lineItems = new ArrayList<>();
        lineItems.add(LineItem.builder().part(part1).quantity(2).build());
        lineItems.add(LineItem.builder().part(part2).quantity(1).build());


        order1 = orderRepository.save(
                Order.builder()
                        .date(new Date())
                        .paid(true)
                        .buyer(user1)
                        .lineItems(lineItems)
                        .build()
        );

        List<LineItem> lineItems2 = new ArrayList<>();
        lineItems2.add(LineItem.builder().part(part2).quantity(9).build());

        order2 = orderRepository.save(
                Order.builder()
                        .date(new Date())
                        .paid(false)
                        .buyer(user1)
                        .lineItems(lineItems2)
                        .build()
        );

        List<LineItem> lineItems3 = new ArrayList<>();
        lineItems3.add(LineItem.builder().part(part2).quantity(2).build());
        order3 = orderRepository.save(
                Order.builder()
                        .date(new Date())
                        .paid(true)
                        .buyer(user2)
                        .lineItems(lineItems3)
                        .build()
        );
    }


    //Zadaj zapytania wykorzystując crudowe repozytorium springa
    //1. liczba transakcji
    //2. liczba transakcji konkretnego produktu
    //3. liczba transakcji konkretnego użytkownika
    //4. użytkownika według maila
    //5. produkty według przedziału cen
    @Test
    void shouldFindAllOrders() {
        // When
        long orderCount = orderRepository.count();

        // Then
        assertEquals(3, orderCount);
    }

    @Test
    void shouldFindOrdersByPart() {
        // When
        List<Order> orders = orderRepository.findByLineItems_Part(part2);
        for (Order order : orders){
            System.out.println(order);
        }

        // Then
        assertEquals(3, orders.size());
    }

    @Test
    void shouldFindOrdersByUser() {
        // When
        List<Order> orders = orderRepository.findByBuyer(user1);

        // Then
        assertEquals(2, orders.size());
    }

    @Test
    void shouldFindUserByEmail() {
        // When
        UserModel user = userRepository.findByEmail(user1.getEmail());

        // Then
        assertEquals(user1, user);
    }

    @Test
    void shouldFindPartsByPriceRange() {
        // When
        List<Part> parts = partRepository.findByPriceBetween(0, 1000);

        // Then
        assertEquals(2, parts.size());
    }
}
