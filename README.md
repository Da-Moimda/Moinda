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

- **API 문서화 필요**
- Port: 9090
- Connection Url : moinda-db
- Database : moinda

```dockerfile
./gradlew clean build
```

```dockerfile
docker-compose up (--build)
```

