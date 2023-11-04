package com.k8slearning.utils;

public interface Constants {

	public void clear();

	public static final class Role {
		private Role() {
			super();
		}

		public static final String ADMINISTRATOR = "ADMINISTRATOR";
		public static final String ROLES_CREATE = "hasRole('1000')";
		public static final String ROLES_READ = "hasRole('1001')";
		public static final String ROLES_UPDATE = "hasRole('1002')";
		public static final String ROLES_DELETE = "hasRole('1003')";

	}

	public static final class Privileges {
		private Privileges() {
			super();
		}

		public static final String PRIVILEGE_CREATE = "hasRole('1004')";
		public static final String PRIVILEGE_READ = "hasRole('1005')";
		public static final String PRIVILEGE_UPDATE = "hasRole('1006')";
		public static final String PRIVILEGE_DELETE = "hasRole('1007')";
	}

	public static final class User {
		private User() {
			super();
		}

		public static final String USER_CREATE = "hasRole('1008')";
		public static final String USER_READ = "hasRole('1009')";
		public static final String USER_UPDATE = "hasRole('1010')";
		public static final String USER_DELETE = "hasRole('1011')";
		public static final String USER_ROLE_ASSIGN = "hasRole('1012')";
	}

	public static final class Status {
		private Status() {
			super();
		}

		public static final String SUCCESS = "Success";
		public static final String WARNING = "Warning";
		public static final String ERROR = "Error";
	}

	public static final class ResponseStatus {
		private ResponseStatus() {
			super();
		}

		public static final String USER_SAVED = "User saved sucessfully...";
		public static final String USER_ALREADY_EXISTS = "Cannot create user more than once...";
		public static final String INITIAL_USER_SAVED = "Initial user saved sucessfully...";
		public static final String INITIAL_USER_EXISTS = "Cannot create admin more than once...";
		public static final String ROLE_SAVED = "Role saved successfully...";
		public static final String ROLE_ALREAY_EXISTS = "Cannot create same role more than once...";
		public static final String INITIALROLE_SAVED = "Initial role saved successfully...";
		public static final String INITIAL_ROLE_EXISTS = "Cannot create admin role more than once...";
	}

	public static final class Api {
		private Api() {
			super();
		}

		public static final String WHITELIST_AUTH_URL = "/v1/auth/**";
		public static final String WHILTELIST_SETUP_URL = "/v1/setup/**";
		public static final String WHITELIST_H2_URL = "/h2-console/**";
	}

	public static final class Topics {
		private Topics() {
			super();
		}

		public static final String PRIVILEGE_TOPIC = "/topic/privileges";
		public static final String ROLE_TOPIC = "/topic/role";
		public static final String USER_TOPIC = "/topic/user";

	}
}
