package com.here.deployment.cloudconfig.model.dcos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.cloudconfig.model.dcos.Label;

@RunWith(SpringRunner.class)
public class LabelTest {
	
	Label label;
	
	ArrayList<String> args;
	
	String arg;
	
	@Before
	public void setUp() {
		label = new Label();
		args = new ArrayList<>();
		arg = "arg";
		args.add(arg);
	}
	
	@Test
	public void testDefensiveCopyGetNull() {
		assertThat(label.getArgs(), nullValue());
	}
	
	@Test
	public void testDefensiveCopyGet() {	
		label.args = args;		
		assertThat(label.getArgs(), not(nullValue()));
		assertThat(label.getArgs(), hasItem(equalTo("arg")));
		assertThat(label.getArgs(), not(sameInstance(args)));
	}
	
	@Test
	public void testDefensiveCopySetNull() {
		label.setArgs(null);
		assertThat(label.getArgs(), not(nullValue()));
		assertThat(label.getArgs(), hasSize(0));
	}
	
	@Test
	public void testDefensiveCopySet() {
		label.setArgs(args);
		assertThat(label.args, not(nullValue()));
		assertThat(label.args, hasItem(equalTo("arg")));
		assertThat(label.args, not(sameInstance(args)));
	}
}
