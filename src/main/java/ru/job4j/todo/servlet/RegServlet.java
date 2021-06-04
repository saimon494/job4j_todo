package ru.job4j.todo.servlet;

import com.google.gson.JsonObject;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HbmStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject json = new JsonObject();
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        var store = HbmStore.instOf();
        if (store.findUserByEmail(email) != null) {
            json.addProperty("result", false);
            json.addProperty("msg", "Email уже занят");
        } else {
            store.save(new User(name, email, password));
            json.addProperty("result", true);
        }
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(json);
        writer.flush();
    }
}
