INSERT INTO locations_list(id, name, is_group, parent_id)
VALUES (1, 'Россия',    TRUE, NULL),
       (2, 'Украина',   TRUE, NULL),
       (3, 'Беларусь',  TRUE, NULL),
       (4, 'Германия',  TRUE, NULL),
       (1001, 'Республика Татарстан',   TRUE, 1),
       (1002, 'Нижегородская Область',  TRUE, 1),
       (1003, 'Донецкая Область',       TRUE, 2),
       (1000001, 'Москва',          FALSE, 1),
       (1000002, 'Санкт-Петербург', FALSE, 1),
       (1000003, 'Казань',          FALSE, 1001),
       (1000004, 'Альметьевск',     FALSE, 1001),
       (1000005, 'Наб. Челны',      FALSE, 1001),
       (1000006, 'Нижний Новгород', FALSE, 1002),
       (1000007, 'Киев',            FALSE, 2),
       (1000008, 'Артемовск',       FALSE, 1003),
       (1000009, 'Донецк',          FALSE, 1003),
       (1000010,   'Минск',           FALSE, 3),
       (2000001,   'Берлин',          FALSE, 4)
;-----------------------------------------------------------------------------------------------------------------------
INSERT INTO professions_list(id, name, is_group, parent_id)
VALUES (1,      'IT/Связь',                 TRUE,   NULL),
       (1001,   'Программист',              FALSE,  1),
       (1002,   'Системный администратор',  FALSE,  1),
       (1003,   'Тестировщик',              FALSE,  1),
       (2,      'Наука и Образование',      TRUE,   NULL),
       (2001,   'Преподаватель',            FALSE,  2),
       (3,      'Транспорт / логистика',    TRUE,   NULL),
       (3001,   'Грузоперевозки',           FALSE,  3),
       (3002,   'Логистика',                FALSE,  3),
       (3003,   'Водитель',                 FALSE,  3),
       (4,      'Туризм / Гостиницы ' ||
                '/ Рестораны',              TRUE,  NULL),
       (4001,   'Управление гостиницами',   FALSE, 4),
       (4002,   'Управление ресторанами',   FALSE, 4),
       (5,      'Продажы',                  TRUE,  NULL),
       (5001,   'Текстиль, оджеда, ' ||
                'обувь (продажи)',          FALSE, 5),
       (6,      'Административный персонал',TRUE,  NULL),
       (6001,   'Учет товарооборота ',      FALSE, 6)
;-----------------------------------------------------------------------------------------------------------------------
INSERT INTO employments_list(id, name)
VALUES (1, 'Постоянна (полный рабочий день)'),
       (2, 'Частичная (свободный график)'),
       (3, 'Проектная (разовые работы)'),
       (4, 'Стажировка')
;-----------------------------------------------------------------------------------------------------------------------
INSERT INTO rates_list(id, name)
VALUES (1, 'Месяц'),
       (2, 'День'),
       (3, 'Час')
;-----------------------------------------------------------------------------------------------------------------------
INSERT INTO types_list(id, name)
VALUES (1, 'У работодателя'),
       (2, 'Дистанционно');
;-----------------------------------------------------------------------------------------------------------------------
INSERT INTO languages_list(id, name)
VALUES (1, 'Английский'),
       (2, 'Немецкий'),
       (3, 'Французский')
;-----------------------------------------------------------------------------------------------------------------------
INSERT INTO language_levels_list(id, name)
VALUES (1, 'Базовый'),
       (2, 'Продвинутый'),
       (3, 'Профессиональный')
;-----------------------------------------------------------------------------------------------------------------------
INSERT INTO categories_list(id, name)
VALUES (1, 'A мотоциклы'),
       (2, 'B легковые'),
       (3, 'C грузовые'),
       (4, 'D автобусы'),
       (5, 'E прицепы');
;-----------------------------------------------------------------------------------------------------------------------
INSERT INTO companies_list(id, name)
VALUES (1, 'Татнефт'  ),
       (2, '1С:Рарус'  ),
       (3, 'Лицей № 131' ),
       (4, 'Автошкола СВЕТОФОР'),
       (5, 'it-academy'),
       (6, 'MANGO OFFICE'),
       (7, '1С-ЦПП'),
       (8, 'Альфа-банк'),
       (9, 'Azimut Hotels'),
       (10, 'DHL-Express'),
       (11, 'Омитранс'),
       (12, 'Acoola Kids'),
       (13, 'Bistro Le Moujik (Бистро Ле Мужик)'),
       (14, 'Сбер-банк');
------------------------------------------------------------------------------------------------------------------------
--||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||--
------------------------------------------------------------------------------------------------------------------------
INSERT INTO vacancies(id, name,   date,    salaryMin, salaryMax, rate_id, location_id, company_id, car, description) VALUES
(1, '1с Программист',               '2019-06-26',   50000,  0,      NULL,   1000003,    2,  0, 'В компанию 1С-франчайзи ' ||
    'требуется программист со знанием платформы 1С:Предприятие8'),
(2, 'JAVA Программист',             '2020-10-05',   60000,  100000, 1,      1000002,    8,  0, 'Открыта вакансия ' ||
    'программиста со знанием JAVA в крупнейший российский банк'),
(3, 'Учитель английского языка',    '2018-01-31',   50000,  50000,  1,      1000003,    3,  0, 'В физико-математический ' ||
    'лицей № 131 требуется учетель старших классов с профессиональным знанием английского языка'),
(4, 'Сис. админ. / IT спец. ' ||
    '(фриланс)',                    '2020-08-03',   1500,   0,      3,      1000002,   NULL,1, ''),
(5, 'Преподватель ПДД автошколы',   '2020-01-31',   50000,  50000,  1,      1000002,    4,  0, ' Преподватель курсов ' ||
      'ПДД в автошколу на неполный рабочий день'),
(6, '1С Интегратор',                '2019-12-01',   0,      150000, NULL,   1000004,    1,  0, ' Крупнейшая нефтяная ' ||
    'компания Татарстана ищет высококвалифицированного специалиста для внедрения ПО <1С> на собственном предприятии'),
(7, 'Настройщик/Тестировщик',       '2020-05-22',   40000,  50000,  1,      1000006,    6,  0, 'В команду разработки ПО ' ||
'виртуальной АТС для нового проекта требуется тестировщик с навыками системного администратора. Конт. лицо Иванов И.И.'),
(8, 'Пеший курьер',                 '2020-01-01',   20000,  30000,  2,      1000001,    10, 0, 'Служба доставки ищет ' ||
'активных людей для работы курьером. Контактное лицо Иван Васильевич'),
(9, 'Преподаватель курсов JAVA',    '2019-04-17',   70000,  85000,  1,      1000003,    5,  0, 'Треубется преподаватель ' ||
'курсов программирования'),
(10, 'Курьер на личном автомобиле', '2020-01-01',   25000,  40000,  2,      1000001,    10, 1, 'Служба доставки ищет ' ||
'активных людей для работы курьером с личным автомобилем. Контактное лицо Иван Васильевич'),
(11, 'Преподаватель курсов 1С',     '2018-01-31',   60000,  75000,  1,      1000003,    7,  0, 'Треубется преподаватель ' ||
'курсов программирования'),
(12, 'Водитель экскурсионного транспорта в ' ||
  'зарубежное представителстьво',     '2020-10-01',   60000,  75000,  1,      2000001,    9,  0, 'Сеть отечественно-зарубежных ' ||
'отелей и гостиниц ищет водителя автобуса со знанием иностранного языка'),
(13, 'Упрвляющий отелем в ' ||
  'зарубежное представительство',     '2020-11-01',   80000,  90000,  1,      2000001,    9,  0, 'Сеть отечественно-зарубежных ' ||
'отелей и гостиниц ищет администратора гостиничного комплекса со знанием иностранного языка'),
(14, 'Личный водитель в ' ||
  'региональное представительство', '2020-12-01',   80000,  100000, 1,      1000001,    9,  1, 'Сеть отечественно-зарубежных ' ||
'отелей и гостиниц ищет водителя-владельца легкового автомобиля представительского класса со знанием иностранного языка'),
(15, 'С++ Программист',             '2020-02-02',   90000,  110000, 1,      1000001,    14, 0, 'Открыта вакансия ' ||
'программиста со знанием C++ в самый крупный банк россии'),
(16, 'Водитель-дальнобойщик ' ||
     '(категории B,C,E)',           '2019-06-01',   80000,  120000, 1,      1000003,    11, 0, 'Логистической компании ' ||
    'требуются водители большегрузов'),
(17, 'Разработчик 1С',              '2019-12-01',   1000,   1000,   3,      1000001,    6,  0, 'В команду разработки ПО ' ||
'виртуальной АТС для нового проекта требуется сертифицированный 1С-программист. Конт. лицо Иванов И.И.'),
(18, 'Инструктор по вождению',      '2020-02-01',   1500,   2000,   3,      1000002,    4,  1, 'Инструктор вождения ' ||
'с личным автомобилем в автошколу на неполный рабочий день'),
(19, 'Продавец-кассир в магазин ' ||
     'детской одежды и обуви',      '2020-07-20',   25000,  30000,  1,      1000005,    12, 0, 'В региональную сеть магазинов ' ||
    'детской одежды требутся продавец'),
(20, 'Администратор французского ' ||
    'бистро на набережной Фонтанки','2020-09-15',   80000,  80000,  1,      1000002,    13, 0, 'В ресторан французской кухни ' ||
'требуется управляющий со знанием французского языка')
;-----------------------------------------------------------------------------------------------------------------------
--||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||--
------------------------------------------------------------------------------------------------------------------------
INSERT INTO vacancies_types(vacancy_id, type_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(4, 2),
(5, 1),
(6, 1),
(6, 2),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(15, 1),
(16, 1),
(17, 2),
(18, 1),
(19, 1),
(20, 1)
;-----------------------------------------------------------------------------------------------------------------------
INSERT INTO vacancies_professions(vacancy_id, profession_id) VALUES
(1, 1001),
(2, 1001),
(3, 2001),
(4, 1002),
(5, 2001),
(6, 1001),
(6, 1002),
(6, 1003),
(7, 1002),
(7, 1003),
(8, 3002),
(9, 1001),
(9, 2001),
(10, 3001),
(11, 1001),
(11, 2001),
(12, 3003),
(13, 4001),
(14, 3003),
(15, 1001),
(16, 3001),
(17, 1001),
(17, 1003),
(18, 3003),
(19, 5001),
(20, 4002)
;-----------------------------------------------------------------------------------------------------------------------
INSERT INTO vacancies_employments(vacancy_id, employment_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 4),
(3, 1),
(4, 2),
(4, 3),
(5, 2),
(6, 1),
(6, 2),
(6, 3),
(7, 1),
(8, 2),
(9, 2),
(10, 2),
(11, 1),
(12, 2),
(13, 1),
(14, 2),
(15, 1),
(15, 4),
(16, 1),
(17, 3),
(18, 2),
(19, 1),
(20, 1)
;-----------------------------------------------------------------------------------------------------------------------
INSERT INTO vacancies_languages(vacancy_id, language_id, level) VALUES
(2, 1, 1),
(3, 1, 3),
(12, 1, 2),
(12, 2, 1),
(13, 1, 3),
(13, 2, 2),
(14, 1, 1),
(15, 1, 1),
(20, 3, 2)
;-----------------------------------------------------------------------------------------------------------------------
INSERT INTO vacancies_categories (vacancy_id, category_id) VALUES
(10, 2),
(12, 2),
(12, 4),
(14, 2),
(16, 2),
(16, 3),
(16, 5),
(18, 2),
(18, 3)
;-----------------------------------------------------------------------------------------------------------------------
