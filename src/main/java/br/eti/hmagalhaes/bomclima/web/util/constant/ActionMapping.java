package br.eti.hmagalhaes.bomclima.web.util.constant;

public enum ActionMapping {

	LOGIN_FORM("GET", "login", false),
	LOGIN_POST("POST", "login", false),
	LOGOUT("GET", "logout", false),
	REGISTER_DEL_FORM("GET", "regdel", true),
	REGISTER_DEL_POST("POST", "regdel", true),
	REGISTER_EDIT_FORM("GET", "regedit", true),
	REGISTER_EDIT_POST("POST", "regedit", true),
	REGISTER_INSERT_FORM("GET", "reginsert", true),
	REGISTER_INSERT_GATEWAY("GET", "reginsertgat", true),
	REGISTER_INSERT_POST("POST", "reginsert", true),
	REGISTER_SEARCH("GET", "reg", true),
	USER_EDIT_FORM("GET", "useredit", true),
	USER_EDIT_POST("POST", "useredit", true),
	USER_INSERT_FORM("GET", "userinsert", true),
	USER_INSERT_POST("POST", "userinsert", true),
	USER_PASSWORD_FORM("GET", "userpass", true),
	USER_PASSWORD_POST("POST", "userpass", true),
	USER_SEARCH("GET", "user", true);

	private final String method;
	private final String operation;

	private final boolean privateService;

	public static ActionMapping fromMethodAndOperation(final String method, final String operation) {
		final String lowcaseOperation = operation.toLowerCase();
		final String lowcaseMethod = method.toLowerCase();

		for (ActionMapping mapping : ActionMapping.values()) {
			if (!mapping.getOperation().toLowerCase().equals(lowcaseOperation)) {
				continue;
			}
			if (!mapping.getMethod().toLowerCase().equals(lowcaseMethod)) {
				continue;
			}
			return mapping;
		}

		return null;
	}

	private ActionMapping(String method, String operation, boolean privateService) {
		this.method = method;
		this.operation = operation;
		this.privateService = privateService;
	}

	public String getMethod() {
		return method;
	}

	public String getOperation() {
		return operation;
	}

	public boolean isPrivateService() {
		return privateService;
	}
}
