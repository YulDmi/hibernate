package com.geekbrains.hibernate;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = factory.createEntityManager();
        Person person = new Person("Bob");
        Person person1 = new Person("Khan");
        Product product = new Product("vine", 1000);
        Person person2 = new Person("Tom");
        Product product1 = new Product("vine", 879);
        Product product2 = new Product("bread", 56);
        Product product3 = new Product("cheese", 570);
        Product product4 = new Product("meet", 230);
        Product product5 = new Product("coca-cоla", 90);

        em.getTransaction().begin();
        em.persist(person);
        em.persist(person1);
        em.persist(person2);
        em.persist(product);
        em.persist(product1);
        em.persist(product2);
        em.persist(product3);
        em.persist(product4);
        em.persist(product5);
        em.getTransaction().commit();




        printLists(em);
        addProductByIdToPerson(em, person.getId(), product1.getId(), product2.getId(), product3.getId());
        addProductByIdToPerson(em, person1.getId(), product3.getId(), product4.getId(), product2.getId());
        getListProductByPersonID(em, person.getId());
        getListProductByPersonID(em, person1.getId());
        getListPersonByProductID(em, 107L);

       deleteProduct(em, product1.getId());
        printLists(em);
        getListPersonByProductID(em,product3.getId());
        getListProductByPersonID(em, person.getId());

        printLists(em);
    }
    public static void deleteProduct(EntityManager em, Long id) {
        Product product = em.find(Product.class, id);
        if (product == null) {
            System.out.println("Error");
        } else {
            em.getTransaction().begin();
            product.getPersons().forEach(person -> person.getProducts().remove(product));
            em.remove(product);
            em.getTransaction().commit();
        }
    }

    private static void getListPersonByProductID(EntityManager em, Long id) {
        em.getTransaction().begin();
        Product product = em.find(Product.class, id);
        em.getTransaction().commit();
        if (product != null){
            System.out.println("Продукт : " + product.getName() + "  приеобретали пользователи : " + product.getPersons());
        }else {
            System.out.println(String.format("Product c %d not exist", id));
        }
    }

    private static void getListProductByPersonID(EntityManager em, Long id) {
        em.getTransaction().begin();
        Person person = em.find(Person.class, id);
        em.getTransaction().commit();
        if (person != null){
            System.out.println("Лист продуктов у пользователя " + person.getName() + " :  " + person.getProducts());
        }else {
            System.out.println(String.format("Person c %d not exist", id));
        }
    }

    private static void printLists(EntityManager em) {
        List<Person> listPerson = em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
        List<Product> listProduct = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        listPerson.forEach(System.out::println);
        listProduct.forEach(System.out::println);
    }

    public static void addProductByIdToPerson(EntityManager em, Long idPerson, Long... idProduct) {
        em.getTransaction().begin();
        Person person = em.find(Person.class, idPerson);
        if(person != null) {
            for (Long l : idProduct) {
                person.addProduct(em.find(Product.class, l));
            }
        }else {
            System.out.println(String.format("Person c %d not exist", idPerson));
        }
        em.getTransaction().commit();
    }
}