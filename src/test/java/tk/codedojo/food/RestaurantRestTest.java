package tk.codedojo.food;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.RestaurantDao;
import tk.codedojo.food.exception.RestaurantException;
import tk.codedojo.food.rest.controller.RestaurantController;
import tk.codedojo.food.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest
public class RestaurantRestTest {
    private MockMvc mockMvc;
    @Mock
    private RestaurantDao restaurantDao;
    @Mock
    private RestaurantService restaurantService;
    @InjectMocks
    private RestaurantController restaurantController;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    }

    @Test
    public void testGetRestaurants() throws Exception {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Boudin", 1d));
        Restaurant restaurant = new Restaurant("1","Boudreauxs",
                "123 street",menuItems);
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(restaurant);
        when(restaurantDao.findAll()).thenReturn(restaurants);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/food/restaurant").accept(MediaType.APPLICATION_JSON);

        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String expected = "[{\"id\":\"1\",\"name\":\"Boudreauxs\",\"address\":\"123 street\",\"menuItems\":[{\"foodItem\":\"Boudin\",\"price\":1.0}]}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testUpdateMenu() throws Exception {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Boudin", 1d));
        List<MenuItem> newMenuItems = new ArrayList<>();
        newMenuItems.add(new MenuItem("Cracklin", 2d));
        when(restaurantService.updateMenu("1", menuItems)).thenReturn(new Restaurant(
                "1", "Boudreauxs", "123 street", newMenuItems));
        MvcResult result = mockMvc.perform(put(
                "/api/food/restaurant/id/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"foodItem\":\"Boudin\",\"price\":1.0}]")
                ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        if (response == null){
            System.out.println(":(");
        } else {
            System.out.println(response.getContentAsString());
        }
    }
}
