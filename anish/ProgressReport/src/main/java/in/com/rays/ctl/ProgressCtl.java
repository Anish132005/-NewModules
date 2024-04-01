package in.com.rays.ctl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.bean.ProgressBean;
import in.com.rays.model.ProgressModel;

@WebServlet("/ProgressCtl")

public class ProgressCtl extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String id = req.getParameter("id");

		System.out.println("id==>" + id);
		System.out.println("doget......progressctl");

		ProgressBean bean = new ProgressBean();
		ProgressModel model = new ProgressModel();

		if (id != null) {
			try {

				bean = model.findbypk(Integer.parseInt(id));

				req.setAttribute("bean", bean);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		RequestDispatcher rd = req.getRequestDispatcher("ProgressView.jsp");
		rd.forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String op = req.getParameter("operation");

		String id = req.getParameter("id");

		String DeveloperName = req.getParameter("DeveloperName");
		String Work = req.getParameter("Work");
		String Target = req.getParameter("Target");
		String LastWeek = req.getParameter("LastWeek");
		String CurrentWeek = req.getParameter("CurrentWeek");
		String Today = req.getParameter("Today");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		ProgressBean bean = new ProgressBean();

		try {
			bean.setDeveloperName(DeveloperName);
			bean.setWork(Work);
			bean.setTarget(Target);
			bean.setLastWeek(LastWeek);
			bean.setCurrentWeek(CurrentWeek);
			bean.setToday(sdf.parse(Today));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		ProgressModel model = new ProgressModel();

		if (op.equals("save")) {

			try {
				model.add(bean);
				req.setAttribute("msg", "User Added Successfully...!!!");

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (op.equals("Delete")) {
			try {
				model.delete(bean.getId());
				;
				;
				req.setAttribute("msg", "User delete Successfully..!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (op.equalsIgnoreCase("update")) {

			try {
				System.out.println("update>>>>>" + bean.getDeveloperName());
				bean.setId(Integer.parseInt(id));
				model.update(bean);
				bean = model.findbypk(bean.getId());
				req.setAttribute("msg", "USER  UPDATE SUSCCESSFULL..!!! ");
				req.setAttribute("bean", bean);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		RequestDispatcher rd = req.getRequestDispatcher("ProgressView.jsp");
		rd.forward(req, resp);

	}

}
