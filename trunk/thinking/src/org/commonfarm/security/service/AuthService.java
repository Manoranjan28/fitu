package org.commonfarm.security.service;

import java.util.HashMap;
import java.util.Map;

import org.commonfarm.security.util.PathMapper;
import org.commonfarm.service.ThinkingService;

/**
 * @author David Yang
 */
public class AuthService extends ThinkingService {

	public void configURLAuth(Map pathRoles, PathMapper pathMapper) {
		//Get all resources with URL type
		Map criterias = new HashMap();
		criterias.put("resType", "URL");
		/*List resources = lookupObject.getObjects(Resource.class, criterias);
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			Resource resource = (Resource) iter.next();
			List roles = new ArrayList();
			for (Iterator itPerm = resource.getPermissions().iterator(); itPerm.hasNext();) {
				Permission permission = (Permission) itPerm.next();
				for (Iterator itRole = permission.getRoles().iterator(); itRole.hasNext();) {
					Role role = (Role) itRole.next();
					roles.add(role.getName());
				}
				
			}
			if (!pathRoles.containsKey(resource.getName())) {
				pathRoles.put(resource.getName(), roles);
			}
			//if (!pathMapper.get)
			pathMapper.put(resource.getName(), resource.getResString());
		}*/
	}
}