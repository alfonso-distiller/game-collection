package net.distiller.gamecollect.services;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import net.distiller.gamecollect.model.Collectible;
import net.distiller.gamecollect.repositories.CollectibleRepository;

public class CollectibleServiceImplTest {
	
	@Mock
	CollectibleRepository repo;
	CollectibleServiceImpl collService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		collService = new CollectibleServiceImpl(repo);
	}
	
	@Test
	public void givenAnGameId_returnAllCollectibles() throws Exception {
		List<Collectible> collectibles = new ArrayList<>();
		
		collectibles.add(Collectible.collected("item1"));
		collectibles.add(Collectible.collected("item2"));
		collectibles.add(Collectible.notCollected("item3"));
		collectibles.add(Collectible.notCollected("item4"));
		
		when(repo.findByGameId(anyLong())).thenReturn(collectibles);
		
		List<Collectible> items = collService.findByGameId(1l);
		
		assertThat(items, not(equalTo(nullValue())));
		assertThat(items, IsCollectionWithSize.hasSize(equalTo(4)));
		
		
		verify(repo, times(1)).findByGameId(anyLong());
        verifyNoMoreInteractions(repo);
	}
	
	@Test
	public void givenAnGameId_returnNothing() throws Exception {
		when(repo.findByGameId(anyLong())).thenReturn(Collections.emptyList());
		
		List<Collectible> items = collService.findByGameId(1l);
		
		assertThat(items, not(equalTo(nullValue())));
		assertThat(items, IsEmptyCollection.empty());
		
		verify(repo, times(1)).findByGameId(anyLong());
        verifyNoMoreInteractions(repo);
	}
	
	@Test
	public void givenAnGameId_returnNothingNullValue() throws Exception {
		when(repo.findByGameId(any())).thenReturn(Collections.emptyList());
		
		List<Collectible> items = collService.findByGameId(null);
		
		assertThat(items, not(equalTo(nullValue())));
		assertThat(items, IsEmptyCollection.empty());
		
		verify(repo, times(1)).findByGameId(any());
        verifyNoMoreInteractions(repo);
	}
	
	@Test
	public void givenAnGameIdAndCollectedFlag_returnAllCollectibles() throws Exception {
		List<Collectible> collectibles = new ArrayList<>();
		
		collectibles.add(Collectible.collected("item1"));
		collectibles.add(Collectible.collected("item2"));
		collectibles.add(Collectible.notCollected("item3"));
		collectibles.add(Collectible.notCollected("item4"));
		
		when(repo.findByGameIdAndIsCollected(anyLong(), anyBoolean()))
			.thenReturn(collectibles.stream()
					.filter(col -> col.isCollected())
					.collect(Collectors.toList()));
		
		List<Collectible> items = collService.findByGameIdAndIsCollected(1l, false);
		
		assertThat(items, not(equalTo(nullValue())));
		assertThat(items, IsCollectionWithSize.hasSize(equalTo(2)));
		
		verify(repo, times(1)).findByGameIdAndIsCollected(anyLong(), anyBoolean());
        verifyNoMoreInteractions(repo);
	}
	
	@Test
	public void givenAnGameIdAndCollectedFlag_returnNothing() throws Exception {
		when(repo.findByGameIdAndIsCollected(anyLong(), anyBoolean()))
		.thenReturn(Collections.emptyList());
		
		List<Collectible> items = collService.findByGameIdAndIsCollected(1l, false);
		
		assertThat(items, not(equalTo(nullValue())));
		assertThat(items, IsEmptyCollection.empty());
		
		verify(repo, times(1)).findByGameIdAndIsCollected(anyLong(), anyBoolean());
        verifyNoMoreInteractions(repo);
	}
	
	@Test
	public void givenAnGameIdAndCollectedFlag_returnNothingNullValue() throws Exception {
		when(repo.findByGameIdAndIsCollected(any(), anyBoolean()))
		.thenReturn(Collections.emptyList());
		
		List<Collectible> items = collService.findByGameIdAndIsCollected(null, false);
		
		assertThat(items, not(equalTo(nullValue())));
		assertThat(items, IsEmptyCollection.empty());
		
		verify(repo, times(1)).findByGameIdAndIsCollected(any(), anyBoolean());
        verifyNoMoreInteractions(repo);
	}
}
