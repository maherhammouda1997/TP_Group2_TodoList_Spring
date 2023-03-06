package fr.m2i.tpSpring.service;

import fr.m2i.tpSpring.model.Todo;
import fr.m2i.tpSpring.model.User;
import fr.m2i.tpSpring.model.Urgency;

import java.util.List;


public interface TodoDao {
    List<Todo> getAllTodos();
    List<Todo> getTodosByUser(int userId);
    List<Todo> getTodosByUrgency(int urgencyId);
    List<Todo> getTodosByUserAndUrgency(int userId, int urgencyId);
    //Todo getTodoById(int id);
    String addTodo(Todo todo, int userId, int urgencyId);
    String updateTodo(Todo todo, int newUserId, int newUrgencyId);
    String deleteTodoById(int id);
    String deleteAllTodosByUser(int userId);
    List<User> getAllUsers();
    User getUserById(int id);
    User addUser(User user);
    User updateUser(User user);
    void deleteUserById(int id);
    List<Urgency> getAllUrgencies();
    Urgency getUrgencyById(int id);
    Urgency addUrgency(Urgency urgency);
    Urgency updateUrgency(int id, Urgency urgency);
    void deleteUrgencyById(int id);
}


