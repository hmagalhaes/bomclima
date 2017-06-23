package br.eti.hmagalhaes.bomclima.web.util.constant;

public enum ViewMapping {

	USER_EDIT("/WEB-INF/view/user/edit.jsp"),
	USER_SEARCH("/WEB-INF/view/user/search.jsp"),
	USER_INSERT("/WEB-INF/view/user/insert.jsp"),
	USER_PASSWORD_EDIT("/WEB-INF/view/user/password.jsp"),
	LOGIN("/WEB-INF/view/login.jsp"),
	ERROR("/WEB-INF/view/error.jsp"),
	REGISTER_SEARCH("/WEB-INF/view/register/search.jsp"),
	REGISTER_INSERT_GATEWAY("/WEB-INF/view/register/insert-gateway.jsp"),
	REGISTER_INSERT("/WEB-INF/view/register/insert.jsp"),
	REGISTER_EDIT_FORM("/WEB-INF/view/register/edit.jsp"),
	DELETE_CONFIRM_FORM("WEB-INF/view/register/delete.jsp");

	private final String view;

	ViewMapping(final String view) {
		this.view = view;
	}

	public String getView() {
		return view;
	}

	@Override
	public String toString() {
		return view;
	}
}
