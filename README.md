# BeatBonds
BeatBonds initial spring batch service which pulls population and followers of artist from spotify api and place it in database to calculate pricing of the artist.

### Task List
<input type="checkbox" checked style="margin-right: 10px; color: blue;"> POC of ItemProcessor, ItemWriter, ItemReader.
<br>
<input type="checkbox" checked style="margin-right: 10px; color: blue;">Testing with Sporify API.
<br>
<input type="checkbox" style="margin-right: 10px; color: blue;">Code Cleanup and Refractoring.
<br>
<input type="checkbox" style="margin-right: 10px; color: blue;">Deployment for data analytics. (See how popularity and followers changes according to time, Graph and dashboard.)
<br>
<input type="checkbox" style="margin-right: 10px; color: blue;">Add Pricing calculation algorithm in analytics.
<br>
<input type="checkbox" style="margin-right: 10px; color: blue;">Observe for ~4 Weeks.
<br>
<input type="checkbox" style="margin-right: 10px; color: blue;">POC with Kafka.
<br>
<input type="checkbox" style="margin-right: 10px; color: blue;">Test Spotify api with kafka and database.
<br>


- 1st Run with 100 artist (`Single Thread`) : 24 second in total with full job, 22sec553ms according to batch job log.
- 2nd Run with 100 artist (`Single Thread`) : 26 second in total with full job, 24s276ms according to batch job log.
- 3rd Run with 100 artist (`Multi Thread CORE: 6, Pool Size: 20`) : 8 second in total with full job, 6s213ms according to batch job log.