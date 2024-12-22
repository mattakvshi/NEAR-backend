INSERT INTO Notification_options (options_id, title, color, bg_color, color_dark, bg_color_dark)
VALUES
(1, 'Telegram', '#294F74', 'rgb(16%, 31%, 45%, 0.3)', '#152376', 'rgb(96%, 96%, 96%, 0.6)'),
(2, 'Email', '#595B82', 'rgb(35%, 36%, 51%, 0.3)', '#f55477', 'rgb(52%, 14%, 32%, 0.7)'),
(3, 'Mobile Notification', '#FF4C2B', 'rgb(100%, 30%, 17%, 0.3)', '#FF4C2B', 'rgb(100%, 30%, 17%, 0.45)')
ON CONFLICT (options_id) DO NOTHING;

INSERT INTO Emergency_type (type_id, title, color, bg_color)
VALUES
(1, 'Earthquake', '#2D384A', 'rgb(18%, 22%, 29%, 0.3)'),
(2, 'Flood', '#236', 'rgb(13%, 20%, 40%, 0.3)'),
(3, 'Tsunami', '#214678', 'rgb(13%, 27%, 47%, 0.3)'),
(4, 'Hurricane', '#595B82', 'rgb(35%, 36%, 51%, 0.3)'),
(5, 'Forest fire', '#FF4C2B', 'rgb(100%, 30%, 17%, 0.3)'),
(6, 'Terrorist attack', '#965443', 'rgb(59%, 33%, 26%, 0.3)')
ON CONFLICT (type_id) DO NOTHING;