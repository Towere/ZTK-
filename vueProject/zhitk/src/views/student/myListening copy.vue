<template>
  <div>
    <h1>Your Target Component</h1>
    <div>
      <img :src="examData.photo" alt="" />
    </div>
    
    <div>
      <h2>{{ examData.source }}</h2>
    </div>
    
    <div>
      <p>{{ examData.content }}</p>
    </div>

    <div>
      <audio controls>
        <source :src="examData.filePath" type="audio/mp3">
        Your browser does not support the audio tag.
      </audio>
    </div>
  </div>
</template>

<script>
export default {
  name: "YourTargetComponent",
  created() {  
    this.examData = this.$store.state.examData;
  },
  data() {
    return {
      examData: {}, 
    };
  },
};
</script>
