package com.marcindziedzic.posts.services;

import com.marcindziedzic.posts.data.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class DeletePostServiceTest {

    @Mock PostRepository repository;
    @InjectMocks DeletePostService service;

    @Test
    public void shouldDeletePost() {
        service.run("id");
        verify(repository).delete("id");
    }

    @Test
    public void shouldNotDeletePostIfIdIsNotGiven() {
        service.run(null);
        verifyZeroInteractions(repository);
    }

    @Test
    public void shouldNotDeletePostIfIdIsEmpty() {
        service.run(" ");
        verifyNoMoreInteractions(repository);
    }
}
