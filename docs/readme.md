Add a tweak by follow this:

```json
"tasks": {

    "Drop Item Clear": {

      "Freq": 300,

      "Cmd": "kill @e[type=minecraft:item]",

      "Notif": "All Drop item will be clear after 30s",

      "NotifBeforeRun": 30,

      "ExecuteNotifEnable": 0
    }
}
```

- Drop Item Clear: name of tweak

- Freq: frequency (second)

- Cmd: Minecraft command

- Notif: Notification to player

- NotifBeforRun: Notification before execute the command, type -1 to disable

- ExecuteNotifEnable: Enable/Disable the execute notification (1/0)

----

Thêm lệnh theo dạng:

```json
"tasks": {

    "Drop Item Clear": {

      "Freq": 300,

      "Cmd": "kill @e[type=minecraft:item]",

      "Notif": "All Drop item will be clear after 30s",

      "NotifBeforeRun": 30,

      "ExecuteNotifEnable": 0
    }
}
```

- Drop Item Clear: Tên lệnh

- Freq: Tần suất gọi lệnh (giây)

- Cmd: Lệnh

- Notif: Thông báo 

- NotifBeforRun: Thông báo trước khi thực thi (giây), nhập -1 để vô hiệu

- ExecuteNotifEnable: có hiện thông báo sau khi chạy lệnh (có hoặc không)
