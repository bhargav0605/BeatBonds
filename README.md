# BeatBonds
BeatBonds initial spring batch service which pulls population and followers of artist from spotify api and place it in database to calculate pricing of the artist.

### Task List
- [x] ~~POC of ItemProcessor, ItemWriter, ItemReader.~~
- [x] ~~Testing with Sporify API.~~
- [x] ~~Code Cleanup and Refractoring.~~
- [ ] Deployment for data analytics. (See how popularity and followers changes according to time, Graph and dashboard.) 
- [ ] Add Pricing calculation algorithm in analytics.
- [ ] Observe for ~4 Weeks.
- [ ] POC with Kafka.
- [ ] Test Spotify api with kafka and database.



- 1st Run with 100 artist (`Single Thread`) : 24 second in total with full job, 22sec553ms according to batch job log.
- 2nd Run with 100 artist (`Single Thread`) : 26 second in total with full job, 24s276ms according to batch job log.
- 3rd Run with 100 artist (`Multi Thread CORE: 6, Pool Size: 20`) : 8 second in total with full job, 6s213ms according to batch job log.