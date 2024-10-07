package org.example.esercizio1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Main {
    public static void main(String[] args) {

       Product prodotto1 = new Product(20, "Games", "Logitech G Pro", 1);
       Product prodotto2 = new Product(120, "PC", "Monitor MSI", 2);
       Product prodotto3 = new Product(101, "Books", "Libro Astronomia", 3);

       List<Product> listaProdotti = new ArrayList<>();
       listaProdotti.add(prodotto1);
       listaProdotti.add(prodotto2);
       listaProdotti.add(prodotto3);

        LocalDate orderDate = LocalDate.now();
        LocalDate deliveryDate = orderDate.plusDays(5);

       Customer customer1 = new Customer(1, "Gianni", 10);

       Order ordine1 = new Order(customer1, listaProdotti, deliveryDate, orderDate, "Ordinato", 2);
       Order ordine2 = new Order(customer1, listaProdotti, deliveryDate, orderDate, "Spedito", 4);
       List<Order> listaOrdini = new ArrayList<>();

       listaOrdini.add(ordine1);
       listaOrdini.add(ordine2);

       List<Product> lista = listaProdotti.stream().filter(product -> product.getCategory().equals("Books") && product.getPrice() > 100).toList();
       System.out.println(lista);

       List<Order> lista1 = listaOrdini.stream().filter(order -> order.getProducts().stream()
               .anyMatch(product -> "Baby".equals(product.getCategory()))).toList();

        System.out.println(lista1);

        List<Product> listaProdotti1 = listaProdotti.stream()
                .filter(product -> product.getCategory().equals("Boys"))
                .map(product -> {
            product.setPrice(product.getPrice() * 10 / 100);
            return product;
        }).toList();
        System.out.println(listaProdotti1);

        LocalDate date1 = LocalDate.parse("2021-02-01");
        LocalDate date2 = LocalDate.parse("2021-04-01");

        List<Product> lista2 = listaOrdini.stream().filter(order -> order.getCustomer().getTier() == 2).filter(order -> order.getOrderDate().isAfter(date1) && order.getOrderDate().isBefore(date2)).flatMap(order -> order.getProducts().stream()).toList();

        Map<Customer, List<Order>> mappa = listaOrdini.stream().collect(Collectors.groupingBy(Order::getCustomer));
        mappa.forEach((cliente, ordini) -> {
            System.out.println("Cliente: " + cliente.getName());
            ordini.forEach(order -> System.out.println("Ordine: " + order.getId()));
        });

        Map<Customer, Double>  mappa1 = listaOrdini.stream().collect(Collectors.groupingBy(Order::getCustomer, Collectors.summingDouble(order -> order.getProducts().stream().mapToDouble(Product::getPrice).sum())));
        mappa1.forEach((cliente, totale) -> {
            System.out.println("Cliente: " + cliente.getName() + ", Totale Vendite: " + totale);
        });

        List<Product> lista3 = listaProdotti.stream().sorted(Comparator.comparingDouble(Product::getPrice).reversed()).toList();

      lista3.forEach(product -> System.out.println("--Prodotto: " + product.getName() + " " + "--Costo: " + product.getPrice()));

      double num = listaOrdini.stream().collect(Collectors.averagingDouble(order -> order.getProducts().stream().mapToDouble(Product::getPrice).sum()));
        System.out.println("La media è: " + num);
    }
}