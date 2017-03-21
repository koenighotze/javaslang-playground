package org.koenighotze.txprototype.user.controller;

import static java.util.UUID.randomUUID;
import static javaslang.test.Arbitrary.string;
import static javaslang.test.Gen.choose;
import static org.koenighotze.txprototype.user.controller.ArbitraryEmail.rfcEmail;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;

import javaslang.collection.List;
import javaslang.test.Arbitrary;
import javaslang.test.Gen;
import javaslang.test.Property;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.koenighotze.txprototype.user.UserAdministrationApplication;
import org.koenighotze.txprototype.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author David Schmitz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {UserAdministrationApplication.class, EmbeddedMongoAutoConfiguration.class})
@WebAppConfiguration
public class UserRestControllerPropertyTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        mappingJackson2HttpMessageConverter = List.of(converters)
                                                  .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                                                  .take(1)
                                                  .getOrElseThrow(() -> new RuntimeException("No Converter found!"));
    }

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void create_a_user_using_put() throws Exception {
        Property.def("Should not go boom")
                .forAll(arbitraryUnicodeString(), arbitraryUnicodeString(), arbitraryNick(), rfcEmail())
                .suchThat((String name, String last, String nick, String email) -> putUser(name, last, nick, email) == CREATED.value())
                .check()
                .assertIsSatisfied();
    }

    private int putUser(String name, String last, String nick, String email) throws Exception {
        User user = new User(randomUUID().toString(), name, last, nick, email);
        ResultActions perform = mockMvc.perform(put("/users/{id}", user.getPublicId()).contentType(APPLICATION_JSON)
                                                                                      .content(json(user)));
        return perform.andReturn()
                      .getResponse()
                      .getStatus();
    }

    private Arbitrary<String> arbitraryNick() {
        return string(choose('a', 'z'));
    }

    private Arbitrary<String> arbitraryUnicodeString() {
        Gen<Character> randomUnicode = random -> (char) random.nextInt();
        return string(randomUnicode);
    }

    @SuppressWarnings("unchecked")
    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(o, APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}