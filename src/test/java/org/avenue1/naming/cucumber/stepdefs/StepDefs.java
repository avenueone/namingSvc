package org.avenue1.naming.cucumber.stepdefs;

import org.avenue1.naming.NamingSvcApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = NamingSvcApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
