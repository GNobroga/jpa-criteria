package app;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.criteria.Path;

import app.domain.entities.Client;
import app.domain.entities.Pen;
import app.domain.entities.Worker;
import app.dto.WorkerDTO;
import app.persistence.GenericDAO;

/**
 * @author Gabriel Cardoso Girarde
 */
public class App {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("criteria_persistence");

    private static final EntityManager em;

    static {
        em = emf.createEntityManager();
    }

    public static void main (String ...args) {
        addWorkerData();
        addClientAndPen();
        // System.out.println();
        // selectingWorkerEntity(25L);
        // System.out.println();
        // parameterizedQueryForWorker("from Worker w where w.salary >= :value and w.name = :name", new HashMap<>() {
        //     {
        //         put("value", new BigDecimal(5));
        //         put("name", "Xander");
        //     }
        // });
        // System.out.println();
        // querySingleEntityAttributeSelection();
        //selectWithTuple();

        //selectMutipleValuesSecondCase();

        //selectAndReturnWorkerDTO();
    
    }

    private static void selectingWorkerEntity(Long id) {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(Worker.class);
        var root = query.from(Worker.class);

        // select w from Worker t where t.id > 50
        query.select(root).where(builder.lt(root.get("id"), id)).orderBy(builder.desc(root.get("id")));
        
        var typedQuery = em.createQuery(query);

        typedQuery.getResultList().forEach(System.out::println);
    }

    private static void querySingleEntityAttributeSelection() {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(String.class);
        var root = query.from(Worker.class);

        // select w from Worker w where w = "Gabriel Cardoso";
        query.select(root.get("name")).where(builder.like(root.get("name"), "%Gabriel%"));

        em.createQuery(query).getResultList().forEach(System.out::println);

    }

    private static void selectWithTuple() {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(Tuple.class);
        var root = query.from(Worker.class);

        Path<String> id = root.get("id");
        Path<String> salary = root.get("salary");

        query.multiselect(id.alias("id"), salary.alias("salary"));

        for(var columns: em.createQuery(query).getResultList()) {
            System.out.println(String.format("%s %s", columns.get("id"), columns.get("salary")));
        }
    }

    private static void selectMutipleValuesSecondCase() {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(Object[].class);
        var root = query.from(Worker.class);

        Path<String> id = root.get("id");
        Path<String> salary = root.get("salary");

        query.select(builder.array(id, salary));

        for(var columns: em.createQuery(query).getResultList()) {
            System.out.println(String.format("%s %s", columns[0], columns[1]));
        }
    }


    private static void selectAndReturnWorkerDTO() {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(WorkerDTO.class);
        var root = query.from(Worker.class);

        query.select(builder.construct(WorkerDTO.class, root.get("name"), root.get("salary")));

        var result = em.createQuery(query).getResultList();

        result.forEach(System.out::println);
    }


    private static void addWorkerData() {
        GenericDAO<Worker> dao = new GenericDAO<>(Worker.class, em);
        List<String> names = Arrays.asList(
            "Alice", "Bob", "Charlie", "David", "Eva", "Frank", "Grace", "Hank", "Ivy", "Jack",
            "Kelly", "Liam", "Mia", "Noah", "Olivia", "Peter", "Quinn", "Rachel", "Sam", "Taylor",
            "Ursula", "Victor", "Wendy", "Xander", "Yara", "Zane", "Anna", "Ben", "Catherine", "Daniel",
            "Emma", "Felix", "Gina", "Hugo", "Isabel", "Jacob", "Kylie", "Luke", "Maria", "Nathan",
            "Oscar", "Pamela", "Quincy", "Riley", "Sophia", "Thomas", "Uma", "Vincent", "Willa", "Gabriel Cardoso"
        );
        Collections.shuffle(names);

        dao.startTransaction();

        names.stream()
            .map(name -> new Worker(name, new Random().nextInt(), new Date(), new BigDecimal(new Random().nextDouble(500))))
            .forEach(worker -> dao.save(worker));

        dao.commit();
    }

    private static void addClientAndPen() {
        GenericDAO<Object> dao = new GenericDAO<>(Object.class, em);

        dao.startTransaction();
      
        for (int i = 0; i < 100; i++) {
            Pen pen = new Pen();
            pen.setName("Pen_" + i);
            pen.setColor("BLACK");

            Client client = new Client();
            client.setName("Client_" + i);
            client.setAge(20 + i);
            client.setBirthDate(new Date());
            client.setWallet(new BigDecimal(1000L));

            client.getPens().add(pen);
            pen.setClient(client);
            dao.save(client);
        }

        dao.commit();
    }


    private static void parameterizedQueryForWorker(String jpql, Map<String, Object> parameters) {
        GenericDAO<Object> dao = new GenericDAO<>(Object.class, em);
        dao.findByParameters(jpql, parameters)
            .forEach(System.out::println);
    }
 }