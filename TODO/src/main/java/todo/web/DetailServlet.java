package todo.web;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DetailServlet")
public class DetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DetailServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		Todo dto;
		if (id >= 1) {
			try (TodoDAO dao = new TodoDAO()) {
				dto = dao.detail(id);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		} else {
			// idが0ならば新規なのでデータは取得せず、殻のTodoインスタンスを取得
			dto = new Todo();
			dto.setId(0);
		}
		request.setAttribute("todo",  dto);
		
		RequestDispatcher rd = request.getRequestDispatcher("/detail.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
