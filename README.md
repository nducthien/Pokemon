# Pokemon
1) Add library
    // Rx java
    implementation 'io.reactivex.rxjava2:rxjava:2.1.7'
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'

    // Retrofit2
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'

    // Glide
    annotationProcessor 'com.github.bumptech.glide:compier:4.8.0'
    implementation 'com.github.bumptech.glide:glide:4.8.0'
2) Using http://json2csharp.com/ covert to json
    - Learn more Edit --) find replace very interesting
3) Create Retrofit client
4) Create new Blank Fragment to display List of Pokemon
    - Add toolbar and Fragment to activity_main
    - Create list_item_pokemon
        - android:scaleType="fitXY"
    - Add RecyclerView
5) Custom Adapter for this RecyclerView
6) Can create function to change color of Chip by Type of Pokemon (or use my own function) - you can copy my own function from Github
    - https://www.youtube.com/watch?v=A2-X72_76ro

-------------------------------------------
6) Create Interface IItemclickListener
    - In PokemonListAdapter we using it
    - In onBindViewHolder using holder.setItemClickListener for event clicked
    - Create new Fragment Pokemon Detail
    - Create LocalBroadcast to request MainActivity replace Fragment when we click on Pokemon
    LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(Common.KEY_ENABLE_HOME).putExtra("position", position));
