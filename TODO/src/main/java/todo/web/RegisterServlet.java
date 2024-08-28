package todo.web;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/RegisterServlet")
@MultipartConfig
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// コンストラクタ
	public RegisterServlet() {	super(); }

	// GETリクエストハンドラ
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得する
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String task = request.getParameter("task");
		String inputLimitdate = request.getParameter("limitdate");
		String userid = request.getParameter("userid");
		int status = Integer.parseInt(request.getParameter("status"));
		String filename = request.getParameter("filename");
		// DTOオブジェクトを生成してパラメータを格納する
		Todo dto = new Todo();
		dto.setId(id);
		dto.setTitle(title);
		dto.setTask(task);
		dto.setInputLimitdate(inputLimitdate);
		dto.setUserid(userid);
		dto.setStatus(status);
		dto.setFilename(filename);
		// パラメータのバリデーション処理を行う
		if (!dto.valueCheck()) {
			// エラーがある場合はメッセージをリクエスト属性に格納して詳細ページビューに転送
			request.setAttribute("errorMessages", dto.getErrorMessages());
			request.setAttribute("todo", dto);
			RequestDispatcher rd = request.getRequestDispatcher("/detail.jsp");
			rd.forward(request, response);
			return;
		}

		// ファイルアップロード処理
		Part part = request.getPart("uploadfile");
		for (String cd : part.getHeader("content-disposition").split(";")) {
			cd = cd.trim();
			if (cd.startsWith("filename")) {
				filename = cd.substring(cd.indexOf("=") + 1).trim().replace("\"", "").replace("\\", "/");
				int pos = filename.lastIndexOf("/");
				if (pos >= 0) {
					filename = filename.substring(pos + 1);
				}
				if (filename.length() != 0) {
					String path = request.getServletContext().getRealPath("/");
					part.write(path + filename);
					dto.setFilename(filename);
					break;
				}
			}
		}
		// DAOオブジェクトを生成
		try (TodoDAO dao = new TodoDAO()) {
			if (id == 0) {
				dao.registerInsert(dto);		// データ登録処理
			} else {
				dao.registerUpdate(dto);		// データ更新処理
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
		// 検索画面へ転送する
		RequestDispatcher rd = request.getRequestDispatcher("/SearchServlet");
		rd.forward(request, response);
	}
	// POSTリクエストハンドラ（GETリクエストと同じ処理とする)
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
