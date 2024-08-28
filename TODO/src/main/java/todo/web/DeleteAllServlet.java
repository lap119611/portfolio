package todo.web;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteAllServlet")
public class DeleteAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteAllServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try(TodoDAO dao = new TodoDAO()){
			int result = dao.deleteAll();
			Logger logger = Logger.getLogger(getClass().getName());
			logger.warning(result + "件のタスクを削除しました！！！！！！");
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/SearchServlet");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
