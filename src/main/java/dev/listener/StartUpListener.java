package dev.listener;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import dev.entite.Collegue;
import dev.repository.CollegueRepository;

@Component
public class StartUpListener {

	@Autowired
	private CollegueRepository cr;

	@EventListener(ContextRefreshedEvent.class)
	@Transactional
	public void contextRefreshedEvent() {
		ClassPathXmlApplicationContext contextData = new ClassPathXmlApplicationContext("collegue.xml");
		Collection<Collegue> collegues = (Collection<Collegue>) contextData.getBeansOfType(Collegue.class).values();
		cr.save(collegues);
		contextData.close();
	}
}
