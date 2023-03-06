package fr.m2i.tpSpring.service;

import fr.m2i.tpSpring.model.User;
import fr.m2i.tpSpring.repository.TodoRepository;
import fr.m2i.tpSpring.repository.UrgencyRepository;
import fr.m2i.tpSpring.repository.UserRepository;
import fr.m2i.tpSpring.model.Todo;
import fr.m2i.tpSpring.model.Urgency;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoDaoImpl implements TodoDao {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UrgencyRepository urgencyRepository;
    @Autowired
    private UserRepository userRepository;

    private EntityManager entityManager;

    public TodoDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // -------------------------------------------------------------------------------------------------------------------------------------\\

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    @Transactional
    public List<Todo> getTodosByUser(int userId) {
        List<Todo> todos = entityManager.createNativeQuery("SELECT * FROM todo t JOIN user u ON t.user_id = u.id JOIN urgency ur ON t.urgency_id = ur.id WHERE t.user_id = :userId")
                .setParameter("userId", userId)
                .getResultList();
        return todos != null ? todos : new ArrayList<>();
    }

    @Override
    public List<Todo> getTodosByUrgency(int urgencyId) {
        List<Todo> todos = entityManager.createNativeQuery("SELECT * FROM todo t JOIN user u ON t.user_id = u.id JOIN urgency ur ON t.urgency_id = ur.id WHERE t.user_id = :urgencyId")
                .setParameter("urgencyId", urgencyId)
                .getResultList();
        return todos != null ? todos : new ArrayList<>();
    }

    @Override
    public List<Todo> getTodosByUserAndUrgency(int userId, int urgencyId) {
        List<Todo> todos = entityManager.createNativeQuery("SELECT * FROM todo t JOIN user u ON t.user_id = u.id JOIN urgency ur ON t.urgency_id = ur.id WHERE t.user_id = :userId AND t.urgency_id = :urgencyId")
                .setParameter("userId", userId)
                .setParameter("urgencyId", urgencyId)
                .getResultList();
        return todos != null ? todos : new ArrayList<>();
    }

//    @Override
//    public Todo addTodo(Todo todo) {
//        return todoRepository.save(todo);
//    }

    @Override
    @Transactional
    public String addTodo(Todo todo, int userId, int urgencyId) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user ID: " + userId);
        }
        Urgency urgency = entityManager.find(Urgency.class, urgencyId);
        if (urgency == null) {
            throw new IllegalArgumentException("Invalid urgency ID: " + urgencyId);
        }
        todo.setUser(user);
        todo.setUrgency(urgency);
        entityManager.persist(todo);
        return "Todo added successfully";
    }

    @Override
    @Transactional
    public String updateTodo(Todo todo, int newUserId, int newUrgencyId) {
        entityManager.createNativeQuery("UPDATE todo " +
                        "SET name = :name, " +
                        "description = :description, " +
                        "date = :date, " +
                        "user_id = :newUserId, " +
                        "urgency_id = :newUrgencyId " +
                        "WHERE id = :id")
                .setParameter("name", todo.getName())
                .setParameter("description", todo.getDescription())
                .setParameter("date", todo.getDate())
                .setParameter("newUserId", newUserId)
                .setParameter("newUrgencyId", newUrgencyId)
                .setParameter("id", todo.getId())
                .executeUpdate();
        return "Todo updated successfully";
    }

    @Override
    public String deleteTodoById(int id) {
        todoRepository.deleteById(id);
        return "todo " + id + " deleted.";
    }

    @Override
    @Transactional
    public String deleteAllTodosByUser(int userId) {
        entityManager.createNativeQuery("DELETE FROM todo WHERE user_id = :userId").setParameter("userId", userId).executeUpdate();
        return "All todos for user " + userId + " have been deleted.";
    }

    // -------------------------------------------------------------------------------------------------------------------------------------\\

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------\\

    @Override
    public List<Urgency> getAllUrgencies() {
        return urgencyRepository.findAll();
    }

    @Override
    public Urgency getUrgencyById(int id) {
        return urgencyRepository.findById(id).orElse(null);
    }

    @Override
    public Urgency addUrgency(Urgency urgency) {
        return urgencyRepository.save(urgency);
    }

    @Override
    public Urgency updateUrgency(int id, Urgency urgency) {
        Urgency existingUrgency = urgencyRepository.findById(id).orElse(null);
        if (existingUrgency != null) {
            existingUrgency.setName(urgency.getName());
            return urgencyRepository.save(existingUrgency);
        }
        return null;
    }

    @Override
    public void deleteUrgencyById(int id) {
            urgencyRepository.deleteById(id);
    }
}
