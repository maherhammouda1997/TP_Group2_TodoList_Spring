package fr.m2i.tpSpring.controller;

import fr.m2i.tpSpring.model.User;
import fr.m2i.tpSpring.model.Todo;
import fr.m2i.tpSpring.model.Urgency;
import fr.m2i.tpSpring.service.TodoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/tpSpring")
@CrossOrigin

public class TodoController {
        @Autowired
        private TodoDao todoDao;

        @GetMapping
        public String getMessage() {
            return "Welcome to our Spring Boot project!\n\n"
            +"1. View all todos : /todo\n"
            +"2. View todos by user : /todo/user/{userId}\n"
            +"3. View todos by urgency : /todo/urgency/{urgencyId}\n"
            +"4. View todos by user and urgency : /todo/{userId}/{urgencyId}\n"
            +"5. Add a new todo : /todo\n"
            +"6. Update a todo : /todo/{id}\n"
            +"7. Delete a todo : /todo/{id}\n"
            +"8. Delete all todos for a user : /todo/user/{userId}\n"
            +"9. View all users : /user\n"
            +"10. View a user by ID : /user/{id}\n"
            +"11. Add a new user : /user/{id}\n"
            +"12. Update a user : /user/{id}\n"
            +"13. Delete a user : /user/{id}\n"
            +"14. View all urgencies : /urgency\n"
            +"15. View an urgency by ID : /urgency/{id}\n"
            +"16. Add a new urgency : /urgency\n"
            +"17. Update an urgency : /urgency/{id}\n"
            +"18. Delete an urgency : /urgency/{id}\n";
        }

    // -------------------------------------------------------------------------------------------------------------------------------------\\

        @GetMapping("/todo")
        public List<Todo> getAllTodos() {
            return todoDao.getAllTodos();
        }

        @GetMapping(path = "/todo/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<Todo>> getTodosByUser(@PathVariable("userId") int userId) {
            List<Todo> todos = todoDao.getTodosByUser(userId);
            return ResponseEntity.ok(todos);
}
        @GetMapping("/todo/urgency/{urgencyId}")
        public List<Todo> getTodosByUrgency(@PathVariable("urgencyId") int urgencyId) {
            return todoDao.getTodosByUrgency(urgencyId);
        }

        @GetMapping("/todo/{userId}/{urgencyId}")
        public List<Todo> getTodosByUserAndUrgency(@PathVariable("userId") int userId, @PathVariable("urgencyId") int urgencyId) {
            return todoDao.getTodosByUserAndUrgency(userId, urgencyId);
        }

        @PostMapping("/todo")
        public String addTodo(@RequestBody Todo todo) {
            return todoDao.addTodo(todo, todo.getUser().getId(), todo.getUrgency().getId());
        }

        @PutMapping("/todo/{id}")
        public ResponseEntity<String> updateTodo(@PathVariable("id") int id, @RequestBody Todo todo) {
            todo.setId(id);
            String result = todoDao.updateTodo(todo, todo.getUser().getId(),  todo.getUrgency().getId());
            return ResponseEntity.ok(result);
        }

        @DeleteMapping("/todo/{id}")
            public void deleteTodoById(@PathVariable("id") int id) {
            todoDao.deleteTodoById(id);
        }

        @DeleteMapping("/todo/user/{userId}")
        public void deleteAllTodosByUser(@PathVariable("userId") int userId) {
            todoDao.deleteAllTodosByUser(userId);
        }

        // -------------------------------------------------------------------------------------------------------------------------------------\\
        @GetMapping("/user")
        public List<User> getAllUsers() {
            return todoDao.getAllUsers();
        }

        @GetMapping("/user/{id}")
        public User getUserById(@PathVariable("id") int id) {
            return todoDao.getUserById(id);
        }

        @PostMapping("/user")
        public String addUser(@RequestBody User user) {
            User addedUser = todoDao.addUser(user);
            if (addedUser == null) {
                return "no user found";
            } else {
                return "new uers added";
            }
        }

        @PutMapping("/user/{id}")
        public User updateUser(@PathVariable("id") int id, @RequestBody User user) throws ChangeSetPersister.NotFoundException {
            user.setId(id);
            User updatedUser = todoDao.updateUser(user);
            if (updatedUser == null) {
                throw new ChangeSetPersister.NotFoundException();
            }
            return updatedUser;
        }

        @DeleteMapping("/user/{userId}")
        public void deleteUserById(@PathVariable("userId") int userId) {
            todoDao.deleteUserById(userId);
        }

    // -------------------------------------------------------------------------------------------------------------------------------------\\

        @GetMapping("/urgency")
        public List<Urgency> getAllUrgencies() {
            return todoDao.getAllUrgencies();
        }
        @GetMapping("/urgency/{id}")
        public Urgency getUrgencyById(@PathVariable("id") int id) {
            return todoDao.getUrgencyById(id);
        }

        @PostMapping(path = "/urgency", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public String createUrgency(@RequestBody Urgency urgency) {
            Urgency addedUrgency = todoDao.addUrgency(urgency);
            if (addedUrgency == null) {
                return "no urgency found";
            } else {
                return "new urgency added";
            }
        }
        @PutMapping(path = "/urgency/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public String updateUrgency(@PathVariable("id") int id, @RequestBody Urgency urgency) {
            Urgency updatedUrgency = todoDao.updateUrgency(id, urgency);
            if (updatedUrgency == null) {
                return "error : id not found";
            } else {
                return "urgency updated";
            }
        }

        @DeleteMapping("/urgency/{id}")
        public void deleteUrgencyById(@PathVariable("id") int id) {
            todoDao.deleteUrgencyById(id);
        }
    }
