package com.nextuple.InventoryApi.service;

import com.nextuple.InventoryApi.model.DbSequence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@ExtendWith(MockitoExtension.class)
public class SequenceGeneratorServiceTest {
    @Mock
    private MongoOperations mongoOperations;

    @InjectMocks
    private SequenceGeneratorService sequenceGeneratorService;

    @Test
    public void testGetSequenceNumber() {
        DbSequence dbSequence = new DbSequence();
        dbSequence.setId("test_sequence");
        dbSequence.setSeq(1);
        Query expectedQuery = new Query(Criteria.where("id").is("test_sequence"));
        Update expectedUpdate = new Update().inc("seq", 1);
        FindAndModifyOptions expectedOptions = new FindAndModifyOptions().returnNew(true).upsert(true);
        Mockito.when(mongoOperations.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class), Mockito.eq(DbSequence.class)))
               .thenReturn(dbSequence);
        int sequenceNumber = sequenceGeneratorService.getSequenceNumber("test_sequence");
        Assertions.assertEquals(1, sequenceNumber);
    }
}
