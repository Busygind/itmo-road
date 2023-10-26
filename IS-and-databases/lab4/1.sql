select НЛ."ИМЯ", НС."ЧЛВК_ИД" from "Н_ЛЮДИ" НЛ
left join "Н_СЕССИЯ" НС on НЛ."ИД" = НС."ЧЛВК_ИД"
where НЛ."ФАМИЛИЯ" = 'Иванов' and НС."ИД" < 20000;

create index ns_people_id on "Н_СЕССИЯ"("ЧЛВК_ИД") --b-tree