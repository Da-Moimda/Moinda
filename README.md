### Moinda

Multi Module Project

---

### Architecture & Introduce

[Moinda - Notion](https://jumpy-cylinder-eb2.notion.site/Moinda-6eb1c833b4a747428974cda7c8755ee5)

---

### Branch Flow

| 브랜치명     |용도|
|----------|---|
| master   |현재 배포중|
| feature  |새로운 기능|
| hotfix   |수정|
| develope |개발중|

---

### How to Use

- Port: 9091
- Connection Url : moinda-db
- Database : moinda

```shell
./gradlew clean build
```

```shell
docker-compose up (--build)
```

### Documentation

- Spring Rest Docs
```shell
   localhost:port/docs/index.html
```

