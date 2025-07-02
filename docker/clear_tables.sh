#!/bin/bash

# Настройки
DB_CONTAINER="near_db"       # имя Docker-контейнера с PostgreSQL
DB_NAME="FirstDB4NEAR"       # имя вашей БД
DB_USER="postgres"           # пользователь БД

# Таблицы, которые НЕ нужно очищать
#EXCLUDE_TABLES=("emergency_type" "notification_options")  # <-- добавляй сюда те, что не трогаем

echo "Очистка таблиц в БД '$DB_NAME', кроме: ${EXCLUDE_TABLES[*]}..."

# Получаем список всех таблиц в схеме public
TABLES=$(docker exec -i "$DB_CONTAINER" psql -U "$DB_USER" -d "$DB_NAME" -t -A -c "
    SELECT table_name FROM information_schema.tables
    WHERE table_schema = 'public';
")

# Проверяем, есть ли таблицы
if [ -z "$TABLES" ]; then
  echo "Нет таблиц в схеме public."
  exit 0
fi

# Формируем список таблиц для очистки, исключая те, что в EXCLUDE_TABLES
TABLES_TO_CLEAR=()
for table in $TABLES; do
  exclude=false
  for exclude_table in "${EXCLUDE_TABLES[@]}"; do
    if [[ "$table" == "$exclude_table" ]]; then
      exclude=true
      break
    fi
  done
  if ! $exclude; then
    TABLES_TO_CLEAR+=("$table")
  fi
done

# Если нет таблиц для очистки — выходим
if [ ${#TABLES_TO_CLEAR[@]} -eq 0 ]; then
  echo "Нет таблиц для очистки. Все таблицы находятся в списке исключений."
  exit 0
fi

# Выводим список таблиц, которые будем очищать
echo "Будут очищены следующие таблицы:"
printf ' - %s\n' "${TABLES_TO_CLEAR[@]}"

# Формируем команду TRUNCATE
TABLES_STR=$(IFS=','; echo "${TABLES_TO_CLEAR[*]}")
TRUNCATE_SQL="TRUNCATE TABLE $TABLES_STR CASCADE;"

# Выполняем команду
echo "Выполняется очистка..."
docker exec -i "$DB_CONTAINER" psql -U "$DB_USER" -d "$DB_NAME" -c "$TRUNCATE_SQL"

# Проверяем результат
if [ $? -ne 0 ]; then
  echo "❌ Ошибка при очистке таблиц."
  exit 1
fi

echo "✅ Успешно очищено: ${#TABLES_TO_CLEAR[@]} таблиц."

# Вставка данных в emergency_type
echo "Вставка данных в таблицу emergency_type..."
EMERGENCY_TYPE_INSERT_SQL="
INSERT INTO public.emergency_type (type_id, bg_color, color, title) VALUES
(1, 'rgb(18%, 22%, 29%, 0.3)', '#2D384A', 'Earthquake'),
(2, 'rgb(13%, 20%, 40%, 0.3)', '#236', 'Flood'),
(3, 'rgb(13%, 27%, 47%, 0.3)', '#214678', 'Tsunami'),
(4, 'rgb(35%, 36%, 51%, 0.3)', '#595B82', 'Hurricane'),
(5, 'rgb(100%, 30%, 17%, 0.3)', '#FF4C2B', 'Forest fire'),
(6, 'rgb(59%, 33%, 26%, 0.3)', '#965443', 'Terrorist attack');
"
docker exec -i "$DB_CONTAINER" psql -U "$DB_USER" -d "$DB_NAME" -c "$EMERGENCY_TYPE_INSERT_SQL"

# Проверяем результат
if [ $? -ne 0 ]; then
  echo "❌ Ошибка при вставке данных в таблицу emergency_type."
  exit 1
fi

echo "✅ Данные успешно вставлены в таблицу emergency_type."

# Вставка данных в notification_options
echo "Вставка данных в таблицу notification_options..."
NOTIFICATION_OPTIONS_INSERT_SQL="
INSERT INTO public.notification_options (options_id, bg_color, bg_color_dark, color, color_dark, title) VALUES
(1, 'rgb(16%, 31%, 45%, 0.3)', 'rgb(96%, 96%, 96%, 0.6)', '#294F74', '#152376', 'Telegram'),
(2, 'rgb(35%, 36%, 51%, 0.3)', 'rgb(52%, 14%, 32%, 0.7)', '#595B82', '#f55477', 'Email'),
(3, 'rgb(100%, 30%, 17%, 0.3)', 'rgb(100%, 30%, 17%, 0.45)', '#FF4C2B', '#FF4C2B', 'Mobile Notification');
"
docker exec -i "$DB_CONTAINER" psql -U "$DB_USER" -d "$DB_NAME" -c "$NOTIFICATION_OPTIONS_INSERT_SQL"

# Проверяем результат
if [ $? -ne 0 ]; then
  echo "❌ Ошибка при вставке данных в таблицу notification_options."
  exit 1
fi

echo "✅ Данные успешно вставлены в таблицу notification_options."

echo "Скрипт завершил работу успешно."