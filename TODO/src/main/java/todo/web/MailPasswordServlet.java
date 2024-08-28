package todo.web;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MailPasswordServlet")
public class MailPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MailPasswordServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String mailaddress = request.getParameter("mailaddress");

		Users dto;
		try (UsersDAO dao = new UsersDAO()) {
			dto = dao.getPassword(userid);
		} catch (Exception e) {
			throw new ServletException(e);
		}

		String toAddr = mailaddress;
		String fromAddr = "todo_server@example.com";
		String personName = "TODO System";
		String subject = "パスワード通知";
		String message;
		String password = dto.getPassword();

		if (password == null) {
			message = "userid[" + userid + "] is not found.\nSo we can't tell you password";

		} else {
			message = "password is \n" + password;
		}
		
		SimpleMailSender mail = new SimpleMailSender();
		try {
			mail.sendMessage(toAddr, fromAddr, personName, subject, message);
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/top.html");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
