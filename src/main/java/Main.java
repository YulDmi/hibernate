import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = MyFactory.getFactory();
        EntityManager em = factory.createEntityManager();
//        Person person1 = new Person("Vasya");
//        Person person2 = new Person("Vanya");
        Product product1 = new Product("bread");
        Product product2 = new Product("milk");
        Dao<Person, Long> personDao = new Dao(Person.class);

//        personDao.create(person1);
//        personDao.create(person2);
        Dao<Product, Long> productDao = new Dao(Product.class);
        productDao.create(product1);
        productDao.create(product2);

//        List<Person> persons = personDao.getAll();
        List<Product> products = productDao.getAll();
        //System.out.println(persons);
        System.out.println(products);
        product2.setTitle("cheese");
        //   Person updated = (Person) ;

        System.out.println(productDao.update(product2));
//        System.out.println( personDao.update(person1));

        productDao.delete(product1);
        products = productDao.getAll();
        System.out.println(products);
//        System.out.println(products);
        //  em.getTransaction().commit();

        System.out.println(productDao.get(2L));
    }
}