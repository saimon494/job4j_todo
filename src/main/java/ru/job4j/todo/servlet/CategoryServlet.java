package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.HbmStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Category> categories = HbmStore.instOf().findAllCategory();
        Gson gson = new Gson();
        resp.getWriter().print(gson.toJson(categories));
    }
}
