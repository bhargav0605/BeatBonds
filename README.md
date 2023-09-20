# BeatBonds
BeatBonds initial spring batch service which pulls population and followers of artist from spotify api and place it in database to calculate pricing of the artist.

### Task List
- [x] ~~POC of ItemProcessor, ItemWriter, ItemReader.~~
- [x] ~~Testing with Sporify API.~~
- [x] ~~Code Cleanup and Refractoring.~~
- [x] ~~Add Pricing calculation algorithm in analytics.~~
- [x] ~~Observe for 4 Weeks.~~
- [ ] Create Graph and visualization to see changes in pricing.
- [ ] POC with Kafka.
- [ ] Test Spotify api with kafka and database.
- [ ] User backend creation.
- [ ] Implement MVP (Minimum Viable Product) and deploy it.



- 1st Run with 100 artist (`Single Thread`) : 24 second in total with full job, 22sec553ms according to batch job log.
- 2nd Run with 100 artist (`Single Thread`) : 26 second in total with full job, 24s276ms according to batch job log.
- 3rd Run with 100 artist (`Multi Thread CORE: 6, Pool Size: 20`) : 8 second in total with full job, 6s213ms according to batch job log.