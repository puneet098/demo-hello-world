package demo.restful.service.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserService {
	
	private static List<In28User> users = new ArrayList<In28User>();

	private static int userCount=3;
	
	static {
		users.add(new In28User(1,"Adam",new Date()));
		users.add(new In28User(2,"Eve",new Date()));
		users.add(new In28User(3,"Jack",new Date()));
	}
	
	public List<In28User> retreiveAllUsers(){
		return users;
	}
	
	public In28User save(In28User user) {
		if(user.getId() == null) {
			user.setId(++userCount);
		}
		return user;
	}
	
	public In28User findById(int userId) {
		for(In28User user:users) {
			if(user.getId()==userId) {
				return user;
			}
		}
		return null;
	}
	
	public In28User removeById(int userId) {
		Iterator<In28User> iterator = users.iterator(); 
		while(iterator.hasNext()) {
			In28User user = iterator.next();
			if(user.getId() == userId) {
				iterator.remove();
				return user;			}
		}
		return null;
	}
}
