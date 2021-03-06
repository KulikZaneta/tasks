package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.dto.CreatedTrelloCardDto;
import com.crud.tasks.domain.dto.TrelloBoardDto;
import com.crud.tasks.domain.dto.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {
    @InjectMocks
    TrelloService trelloService;

    @Mock
    TrelloClient trelloClient;


    @Mock
    RestTemplate restTemplate;

    @Mock
    SimpleEmailService simpleEmailService;

    @Mock
    AdminConfig adminConfig;


    @Test
    public void testCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test task", "Test Description", "top", "test_id");
        CreatedTrelloCardDto createdTrelloCard = new CreatedTrelloCardDto(
                "1",
                "Test task",
                "http://test.com"
        );
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCard);
        when(adminConfig.getAdminMail()).thenReturn("admin@test.com");
        //When
        CreatedTrelloCardDto newCard = trelloService.createdTrelloCard(trelloCardDto);
        //Then
        assertEquals("1", newCard.getId());
        assertEquals("Test task", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    public void testFetchTrelloBoards() {
        //Given
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("7", "test board", new ArrayList<>());
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto);
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtoList);
        //When
        List<TrelloBoardDto> fetchedTrelloBoardsList = trelloService.fetchTrelloBoards();
        //Then
        assertEquals(1, fetchedTrelloBoardsList.size());
    }
}
