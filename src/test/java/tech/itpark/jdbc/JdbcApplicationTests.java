package tech.itpark.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JdbcApplicationTests {
    @Autowired
    MockMvc mockMvc;
    @Test
    void crudTest() throws Exception {
        Date currentDate = new Date(System.currentTimeMillis());
//---------------------------------------------------------------------------------------------------------------> G E T
        mockMvc.perform(get("/vacancies/20"))
                .andExpect(content()
                        .json("{\n" +
                                "  \"id\": 20,\n" +
                                "  \"name\": \"Администратор французского бистро на набережной Фонтанки\",\n" +
                                "  \"date\": \"2020-09-15\",\n" +
                                "  \"salaryMin\": 80000,\n" +
                                "  \"salaryMax\": 80000,\n" +
                                "  \"rate\": {\n" +
                                "    \"id\": 1,\n" +
                                "    \"name\": \"Месяц\"\n" +
                                "  },\n" +
                                "  \"location\": {\n" +
                                "    \"id\": 1000002,\n" +
                                "    \"name\": \"Санкт-Петербург\"\n" +
                                "  },\n" +
                                "  \"company\": {\n" +
                                "    \"id\": 13,\n" +
                                "    \"name\": \"Bistro Le Moujik (Бистро Ле Мужик)\"\n" +
                                "  },\n" +
                                "  \"types\": [\n" +
                                "    {\n" +
                                "      \"id\": 1,\n" +
                                "      \"name\": \"У работодателя\"\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"professions\": [\n" +
                                "    {\n" +
                                "      \"id\": 4002,\n" +
                                "      \"name\": \"Управление ресторанами\"\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"employments\": [\n" +
                                "    {\n" +
                                "      \"id\": 1,\n" +
                                "      \"name\": \"Постоянна (полный рабочий день)\"\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"languages\": [\n" +
                                "    {\n" +
                                "      \"id\": 3,\n" +
                                "      \"name\": \"Французский\",\n" +
                                "      \"level\": {\n" +
                                "        \"id\": 2,\n" +
                                "        \"name\": \"Продвинутый\"\n" +
                                "      }\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"categories\": []\n" +
                                "}"));
//----------------------------------------------------------------------------------------------------------------> POST
        mockMvc.perform(post("/vacancies")
                .contentType("application/json").
                        content("{\n" +
                                "  \"id\": 0,\n" +
                                "  \"name\": \"Репетитор-полиглот иностранных языков (английский, немецкий, французский)\",\n" +
                                "  \"salaryMin\": 2000,\n" +
                                "  \"salaryMax\": 2000,\n" +
                                "  \"rate_id\" : 3,\n" +
                                "  \"location_id\": 1000002,\n" +
                                "  \"types_ids\" : [1, 2],\n" +
                                "  \"professions_ids\" : [2001],\n" +
                                "  \"employments_ids\" : [2],\n" +
                                "  \"languages_ids_levels_ids\" : [[1,3],[2,3],[3,3]]\n" +
                                "}"))
                .andExpect(content()
                        .json("{\n" +
                                "  \"id\": 21,\n" +
                                "  \"name\": \"Репетитор-полиглот иностранных языков (английский, немецкий, французский)\",\n" +
                                "  \"date\": \""+currentDate.toString()+"\",\n" +
                                "  \"salaryMin\": 2000,\n" +
                                "  \"salaryMax\": 2000,\n" +
                                "  \"rate\": {\n" +
                                "    \"id\": 3,\n" +
                                "    \"name\": \"Час\"\n" +
                                "  },\n" +
                                "  \"location\": {\n" +
                                "    \"id\": 1000002,\n" +
                                "    \"name\": \"Санкт-Петербург\"\n" +
                                "  },\n" +
                                "  \"company\": null,\n" +
                                "  \"types\": [\n" +
                                "    {\n" +
                                "      \"id\": 1,\n" +
                                "      \"name\": \"У работодателя\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"id\": 2,\n" +
                                "      \"name\": \"Дистанционно\"\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"professions\": [\n" +
                                "    {\n" +
                                "      \"id\": 2001,\n" +
                                "      \"name\": \"Преподаватель\"\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"employments\": [\n" +
                                "    {\n" +
                                "      \"id\": 2,\n" +
                                "      \"name\": \"Частичная (свободный график)\"\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"languages\": [\n" +
                                "    {\n" +
                                "      \"id\": 1,\n" +
                                "      \"name\": \"Английский\",\n" +
                                "      \"level\": {\n" +
                                "        \"id\": 3,\n" +
                                "        \"name\": \"Профессиональный\"\n" +
                                "      }\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"id\": 2,\n" +
                                "      \"name\": \"Немецкий\",\n" +
                                "      \"level\": {\n" +
                                "        \"id\": 3,\n" +
                                "        \"name\": \"Профессиональный\"\n" +
                                "      }\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"id\": 3,\n" +
                                "      \"name\": \"Французский\",\n" +
                                "      \"level\": {\n" +
                                "        \"id\": 3,\n" +
                                "        \"name\": \"Профессиональный\"\n" +
                                "      }\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"categories\": []\n" +
                                "}"));
//----------------------------------------------------------------------------------------------------------------> FIND
        mockMvc.perform(get("/vacancies/find")
                .queryParam("langs", String.valueOf("3")))
                .andExpect(content()
                        .json("{\n" +
                                "  \"page\": 1,\n" +
                                "  \"pages\": 1,\n" +
                                "  \"rows\": 5,\n" +
                                "  \"size\": 2,\n" +
                                "  \"sort\": 0,\n" +
                                "  \"items\": [\n" +
                                "    {\n" +
                                "      \"id\": 20,\n" +
                                "      \"name\": \"Администратор французского бистро на набережной Фонтанки\",\n" +
                                "      \"date\": \"2020-09-15\",\n" +
                                "      \"salaryMin\": 80000,\n" +
                                "      \"salaryMax\": 80000,\n" +
                                "      \"rate\": {\n" +
                                "        \"id\": 1,\n" +
                                "        \"name\": \"Месяц\"\n" +
                                "      },\n" +
                                "      \"location\": {\n" +
                                "        \"id\": 1000002,\n" +
                                "        \"name\": \"Санкт-Петербург\"\n" +
                                "      },\n" +
                                "      \"company\": {\n" +
                                "        \"id\": 13,\n" +
                                "        \"name\": \"Bistro Le Moujik (Бистро Ле Мужик)\"\n" +
                                "      },\n" +
                                "      \"types\": [\n" +
                                "        {\n" +
                                "          \"id\": 1,\n" +
                                "          \"name\": \"У работодателя\"\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"professions\": [\n" +
                                "        {\n" +
                                "          \"id\": 4002,\n" +
                                "          \"name\": \"Управление ресторанами\"\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"employments\": [\n" +
                                "        {\n" +
                                "          \"id\": 1,\n" +
                                "          \"name\": \"Постоянна (полный рабочий день)\"\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"languages\": [\n" +
                                "        {\n" +
                                "          \"id\": 3,\n" +
                                "          \"name\": \"Французский\",\n" +
                                "          \"level\": {\n" +
                                "            \"id\": 2,\n" +
                                "            \"name\": \"Продвинутый\"\n" +
                                "          }\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"categories\": []\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"id\": 21,\n" +
                                "      \"name\": \"Репетитор-полиглот иностранных языков (английский, немецкий, французский)\",\n" +
                                "      \"date\": \""+currentDate.toString()+"\",\n" +
                                "      \"salaryMin\": 2000,\n" +
                                "      \"salaryMax\": 2000,\n" +
                                "      \"rate\": {\n" +
                                "        \"id\": 3,\n" +
                                "        \"name\": \"Час\"\n" +
                                "      },\n" +
                                "      \"location\": {\n" +
                                "        \"id\": 1000002,\n" +
                                "        \"name\": \"Санкт-Петербург\"\n" +
                                "      },\n" +
                                "      \"company\": null,\n" +
                                "      \"types\": [\n" +
                                "        {\n" +
                                "          \"id\": 1,\n" +
                                "          \"name\": \"У работодателя\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"id\": 2,\n" +
                                "          \"name\": \"Дистанционно\"\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"professions\": [\n" +
                                "        {\n" +
                                "          \"id\": 2001,\n" +
                                "          \"name\": \"Преподаватель\"\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"employments\": [\n" +
                                "        {\n" +
                                "          \"id\": 2,\n" +
                                "          \"name\": \"Частичная (свободный график)\"\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"languages\": [\n" +
                                "        {\n" +
                                "          \"id\": 1,\n" +
                                "          \"name\": \"Английский\",\n" +
                                "          \"level\": {\n" +
                                "            \"id\": 3,\n" +
                                "            \"name\": \"Профессиональный\"\n" +
                                "          }\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"id\": 2,\n" +
                                "          \"name\": \"Немецкий\",\n" +
                                "          \"level\": {\n" +
                                "            \"id\": 3,\n" +
                                "            \"name\": \"Профессиональный\"\n" +
                                "          }\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"id\": 3,\n" +
                                "          \"name\": \"Французский\",\n" +
                                "          \"level\": {\n" +
                                "            \"id\": 3,\n" +
                                "            \"name\": \"Профессиональный\"\n" +
                                "          }\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"categories\": []\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}"));
//--------------------------------------------------------------------------------------------------------------> DELETE
        mockMvc.perform(delete("/vacancies/21/remove"))
                .andExpect(content()
                        .json("{\n" +
                                "  \"id\": 21,\n" +
                                "  \"name\": \"Репетитор-полиглот иностранных языков (английский, немецкий, французский)\",\n" +
                                "  \"date\": \""+currentDate.toString()+"\",\n" +
                                "  \"salaryMin\": 2000,\n" +
                                "  \"salaryMax\": 2000,\n" +
                                "  \"rate\": {\n" +
                                "    \"id\": 3,\n" +
                                "    \"name\": \"Час\"\n" +
                                "  },\n" +
                                "  \"location\": {\n" +
                                "    \"id\": 1000002,\n" +
                                "    \"name\": \"Санкт-Петербург\"\n" +
                                "  },\n" +
                                "  \"company\": null,\n" +
                                "  \"types\": [\n" +
                                "    {\n" +
                                "      \"id\": 1,\n" +
                                "      \"name\": \"У работодателя\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"id\": 2,\n" +
                                "      \"name\": \"Дистанционно\"\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"professions\": [\n" +
                                "    {\n" +
                                "      \"id\": 2001,\n" +
                                "      \"name\": \"Преподаватель\"\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"employments\": [\n" +
                                "    {\n" +
                                "      \"id\": 2,\n" +
                                "      \"name\": \"Частичная (свободный график)\"\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"languages\": [\n" +
                                "    {\n" +
                                "      \"id\": 1,\n" +
                                "      \"name\": \"Английский\",\n" +
                                "      \"level\": {\n" +
                                "        \"id\": 3,\n" +
                                "        \"name\": \"Профессиональный\"\n" +
                                "      }\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"id\": 2,\n" +
                                "      \"name\": \"Немецкий\",\n" +
                                "      \"level\": {\n" +
                                "        \"id\": 3,\n" +
                                "        \"name\": \"Профессиональный\"\n" +
                                "      }\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"id\": 3,\n" +
                                "      \"name\": \"Французский\",\n" +
                                "      \"level\": {\n" +
                                "        \"id\": 3,\n" +
                                "        \"name\": \"Профессиональный\"\n" +
                                "      }\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"categories\": []\n" +
                                "}"));
//--------------------------------------------------------------------------------------------------------------------->
    }
}
