package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HbmStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean showAll = Boolean.parseBoolean(req.getParameter("showAll"));
        List<Item> items = HbmStore.instOf().findAllItem();
        if (!showAll) {
            items = items.stream().filter(x -> !x.isDone()).collect(Collectors.toList());
        }
        items.sort(Comparator.comparingInt(Item::getId).reversed());
        Gson gson = new Gson();
        resp.getWriter().print(gson.toJson(items));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        Item item = gson.fromJson(req.getReader().readLine(), Item.class);
        item.setUser((User) req.getSession().getAttribute("user"));
        HbmStore.instOf().save(item);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        int id = Integer.parseInt(req.getParameter("id"));
        var store = HbmStore.instOf();
        Item item = store.findItemById(id);
        item.setDone(true);
        store.save(item);
    }
}
