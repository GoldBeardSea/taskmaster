package com.goldbeardsea.taskmaster;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.goldbeardsea.taskmaster.model.Task;
import com.goldbeardsea.taskmaster.repository.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TaskmasterApplication.class)
@WebAppConfiguration
@ActiveProfiles("local")
public class TaskMasterRepositoryIntegrationTest {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    TaskRepository repository;

    private static final String EXPECTED_TITLE = "Eat food";
    private static final String EXPECTED_DESCRIPTION = "You must eat";
    private static final String EXPECTED_STATUS = "Available";

    @Before
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Task.class);

        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        dynamoDBMapper.batchDelete((List<Task>)repository.findAll());
    }

    @Test
    public void readWriteTestCase() {
        Task dave = new Task(EXPECTED_TITLE, EXPECTED_DESCRIPTION, EXPECTED_STATUS);
        repository.save(dave);

        List<Task> result = (List<Task>) repository.findAll();

        assertTrue("Not empty", result.size() > 0);
        assertTrue("You must eat", result.get(0).getTitle().equals(EXPECTED_TITLE));
    }
}