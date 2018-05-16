# UTest acceptance test example

### Resources
UTest  
https://github.com/lihaoyi/utest

Scala HTTP client  
http://sttp.readthedocs.io/en/latest/index.html

## Prerequisites ##
Install Scala and sbt

## Test case ##
Acceptance test of for fetching a single video from Klaras REST API:   
 
<b>Given</b> there is a video added to ElasticSearch with id 'dn.screen9.1uwHxJLDuuBKBHGHQcissw'  
<b>When</b> fetching a video with id 'dn.screen9.1uwHxJLDuuBKBHGHQcissw'  
<b>Then</b> response should contain the right json body  

```json
{
  "id" : "dn.screen9.1uwHxJLDuuBKBHGHQcissw",
  "fileName" : "20171107-arbogany-1053_NormalHires.mp4",
  "title" : "Grafikfilm: Arbogamorden  (uppdaterad för hovrätten)",
  "description" : "Huvudpersonerna och händelserna som ledde fram till rättegången och dom mot den 42:åriga kvinnan och hennes pojkvän. Nu prövas målet i Svea Hovrätt",
  "streamUrl" : "https://video-cdn.dn.se/M/V/1/u/1uwHxJLDuuBKBHGHQcissw_360p_h264h.mp4?v=1&token=0ed558211ccafe3db4784",
  "thumbnails" : {
    "small" : "https://csp.screen9.com/img/1/u/w/H/thumb_1uwHxJLDuuBKBHGHQcissw/8.jpg",
    "large" : "https://csp.screen9.com/img/1/u/w/H/image_1uwHxJLDuuBKBHGHQcissw/8.jpg"
  },
  "duration" : "PT1M36.28S",
  "publishedAt" : null,
  "createdAt" : "2017-11-07T14:36:46.000+01:00",
  "createdBy" : null,
  "transcodeStatus" : "done",
  "sentToTranscodeAt" : "2017-11-07T14:38:12.000+01:00",
  "status" : "published"
}
```

## Setup test fixture ##
In nav project comment out existing Groovy tests mvn execution in script `.run.sh`, and then it to start containers with ElasticSearch, Redis and nav-klara-dx up and running:
```sh
./integration-test/nav-klara-it/run.sh
```

## Run test with sbt ##
```sh
sbt> testOnly example.NavKlaraVideoSpecAsync 
```

## Run test with IntelliJ ##
Right click on test an choose run

## Test Success Output
```sh
-------------------------------- Running Tests --------------------------------
+ example.NavKlaraVideoSpec.Klara videos.Fetch a single video.Given there is a video added to elasticsearch with id dn.screen9.1uwHxJLDuuBKBHGHQcissw 1341ms

+ example.NavKlaraVideoSpec.Klara videos.Fetch a single video.When fetching a video with id dn.screen9.1uwHxJLDuuBKBHGHQcissw 73ms

+ example.NavKlaraVideoSpec.Klara videos.Fetch a single video.Then the response should contain the right json data 2ms

+ example.NavKlaraVideoSpec.Klara videos.Fetch a single video.Clean up afterwards 12ms  true
Tests: 4, Passed: 4, Failed: 0
[info]
[success] Total time: 3 s, completed May 16, 2018 3:50:00 PM
```

## Test Failure Outout
```sh
-------------------------------- Running Tests --------------------------------
+ example.NavKlaraVideoSpec.Klara videos.Fetch a single video.Given there is a video added to elasticsearch with id dn.screen9.1uwHxJLDuuBKBHGHQcissw 34ms

+ example.NavKlaraVideoSpec.Klara videos.Fetch a single video.When fetching a video with id dn.screen9.1uwHxJLDuuBKBHGHQcissw 31ms

X example.NavKlaraVideoSpec.Klara videos.Fetch a single video.Then the response should contain the right json data 1ms
  utest.AssertionError: assert(getVideoKlaraJson == videoKlaraExpectedJson)
  videoKlaraExpectedJson: String = {
    "id" : "dn.screen9.1uwHxJLDuuBKBHGHQcissw",
    "fileName" : "20171107-arbogany-1053_NormalHires.mp4",
    "title" : "Grafikfilm: Arbogamorden  (uppdaterad för hovrätten)",
    "description" : "Huvudpersonerna och händelserna som ledde fram till rättegången och dom mot den
   42:åriga kvinnan och hennes pojkvän. Nu prövas målet i Svea Hovrätt",
    "streamUrl" : "https://video-cdn.dn.se/M/V/1/u/1uwHxJLDuuBKBHGHQcissw_360p_h264h.mp4?v=1&token=0
  ed558211ccafe3db4784",
    "thumbnails" : {
      "small" : "https://csp.screen9.com/img/1/u/w/H/thumb_1uwHxJLDuuBKBHGHQcissw/8.jpg",
      "large" : "https://csp.screen9.com/img/1/u/w/H/image_1uwHxJLDuuBKBHGHQcissw/8.jpg"
    },
    "duration" : "PT1M36.28S",
    "publishedAt" : null,
    "createdAt" : "2017-11-07T14:36:46.000+01:00",
    "createdBy" : null,
    "transcodeStatus" : "done",
    "sentToTranscodeAt" : "2017-11-07T14:38:12.000+01:00",
    "status" : "publishedXX"
  }
    utest.asserts.Asserts$.assertImpl(Asserts.scala:114)
    example.NavKlaraVideoSpec$.$anonfun$tests$8(NavKlaraVideoSpec.scala:43)
+ example.NavKlaraVideoSpec.Klara videos.Fetch a single video.Clean up afterwards 11ms  true
Tests: 4, Passed: 3, Failed: 1
```

### Pros
+ Very nice output when run in sbt console or in terminal
+ Built in support for retries for flaky test
+ Many ways to run groups of tests
+ Smart way of sharing setup code. Each test gets new copy of variable
+ Can be run by IntelliJ

### Cons
- \<Click to se difference> with IntelliJ does not seem to work.  
    - Workaround: 
        - Debug, breakpoint, copy expected value to clipboard, select actual value and choose 'Compar Value with Clipboard'
- Not very clear output when running tests with IntelliJ
