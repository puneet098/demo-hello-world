package demo.restful.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDAOService {
	
	private static List<User> users = new ArrayList<User>();

	private static int userCount=3;
	
	static {
		users.add(new User(1,"Adam",new Date()));
		users.add(new User(2,"Eve",new Date()));
		users.add(new User(3,"Jack",new Date()));
	}
	
	public List<User> retreiveAllUsers(){
		return users;
	}
	
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(++userCount);
		}
		return user;
	}
	
	public User findById(int userId) {
		for(User user:users) {
			if(user.getId()==userId) {
				return user;
			}
		}
		return null;
	}
	
	public User removeById(int userId) {
		Iterator<User> iterator = users.iterator(); 
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user.getId() == userId) {
				iterator.remove();
				return user;			}
		}
		return null;
	}
}
