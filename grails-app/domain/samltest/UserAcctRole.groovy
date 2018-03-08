package samltest

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.codehaus.groovy.util.HashCodeHelper
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class UserAcctRole implements Serializable {

	private static final long serialVersionUID = 1

	UserAcct userAcct
	Role role

	@Override
	boolean equals(other) {
		if (other instanceof UserAcctRole) {
			other.userAcctId == userAcct?.id && other.roleId == role?.id
		}
	}

    @Override
	int hashCode() {
	    int hashCode = HashCodeHelper.initHash()
        if (userAcct) {
            hashCode = HashCodeHelper.updateHash(hashCode, userAcct.id)
		}
		if (role) {
		    hashCode = HashCodeHelper.updateHash(hashCode, role.id)
		}
		hashCode
	}

	static UserAcctRole get(long userAcctId, long roleId) {
		criteriaFor(userAcctId, roleId).get()
	}

	static boolean exists(long userAcctId, long roleId) {
		criteriaFor(userAcctId, roleId).count()
	}

	private static DetachedCriteria criteriaFor(long userAcctId, long roleId) {
		UserAcctRole.where {
			userAcct == UserAcct.load(userAcctId) &&
			role == Role.load(roleId)
		}
	}

	static UserAcctRole create(UserAcct userAcct, Role role, boolean flush = false) {
		def instance = new UserAcctRole(userAcct: userAcct, role: role)
		instance.save(flush: flush)
		instance
	}

	static boolean remove(UserAcct u, Role r) {
		if (u != null && r != null) {
			UserAcctRole.where { userAcct == u && role == r }.deleteAll()
		}
	}

	static int removeAll(UserAcct u) {
		u == null ? 0 : UserAcctRole.where { userAcct == u }.deleteAll() as int
	}

	static int removeAll(Role r) {
		r == null ? 0 : UserAcctRole.where { role == r }.deleteAll() as int
	}

	static constraints = {
		role validator: { Role r, UserAcctRole ur ->
			if (ur.userAcct?.id) {
				UserAcctRole.withNewSession {
					if (UserAcctRole.exists(ur.userAcct.id, r.id)) {
						return ['userRole.exists']
					}
				}
			}
		}
	}

	static mapping = {
		id composite: ['userAcct', 'role']
		version false
	}
}
