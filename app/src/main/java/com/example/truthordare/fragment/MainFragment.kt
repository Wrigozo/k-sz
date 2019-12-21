package com.example.truthordare.fragment


import androidx.lifecycle.ViewModelProviders
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController

import com.example.truthordare.R
import com.example.truthordare.database.Player
import com.example.truthordare.database.PlayerDatabase
import com.example.truthordare.database.Question
import com.example.truthordare.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var btnTruth: Button
    lateinit var btnDare: Button
    lateinit var btnEnd: Button

    lateinit var textView: TextView

    lateinit var chosenPlayer: TextView

    private var dares = arrayListOf(
        "Serenade the person to your right.",
        "Talk in an accent for the next 3 rounds.",
        "Kiss the person to your left.",
        "Attempt to do a magic trick.",
        "Do four cartwheels in row.",
        "Let someone shave part of your body.",
        "Eat five tablespoons of a condiment.",
        "Be someone’s pet for the next 5 minutes.",
        "Attempt to break dance for 30 seconds.",
        "Let the group give you a new hairstyle.",
        "Do the worm.",
        "Curse like sailor for 20 seconds straight.",
        "Sniff the armpits of everyone in the room.",
        "Dance to a song of the group’s choosing.",
        "Take a shower with your clothes on.",
        "Break two eggs on your head.",
        "Do your best impression of a baby being born.",
        "Belly dance like your life depended on it.",
        "Put 4 ice cubes down your pants.",
        "Lick the floor.",
        "For a guy, put on makeup. For a girl, wash off your make up.",
        "Dance with no music for 1 minute.",
        "Try to drink a glass water while standing on your hands.",
        "Let the group pose you in an embarrassing position and take a picture.",
        "Play a song on by slapping your butt cheeks till someone guesses the song.",
        "Give someone your phone and let them send one text to anyone in your contacts.",
        "Depict a human life through interpretive dance.",
        "Drink a small cup or shot of a concoction that the group makes. (Don’t make anything that might cause bodily harm. No visits to the emergency room.).",
        "Write or draw something embarrassing somewhere on your body (that can be hidden with clothing) with a permanent marker.",
        "Make every person in the group smile, keep going until everyone has cracked a smiled.",
        "Let the person to your left draw on your face with a pen.",
        "Make up a 30 second opera about a person or people in the group and perform it.",
        "Beg and plead the person to your right not to leave you for that other boy or girl. Weeping, gnashing of teeth, and wailing is encouraged.",
        "Do pushups until you can’t do any more, wait 5 seconds, and then do one more.",
        "Sell a piece trash to someone in the group. Use your best salesmanship.",
        "Let the group look through your phone for 1 minute.",
        "Switch clothes with someone of the opposite sex in the group for three rounds.",
        "Imitate a celebrity every time you talk for three minutes.",
        "Try to juggle 2 or 3 items of the group’s choosing.",
        "Stick your arm into the trash can past your elbow.",
        "Walk on your hands from one side of the room to the other. You can ask someone to hold your legs if necessary.",
        "Gargle something that shouldn’t be gargled but won’t hurt you.",
        "Get slapped on the face by the person of your choosing.",
        "Grab a trash can and make a hoop with your hands above the trash can. Have other members of the group try to shoot trash through your impromptu trashketball hoop.\n",
        "Spin an imaginary hula hoop around your waist for 3 minutes while the game continues.",
        "Imitate popular YouTube videos until someone can guess the video you are imitating.",
        "Seduce a member of the same gender in the group.",
        "Compose a poem on the spot based on something the group comes up with.",
        "Poll dance for 1 minute with an imaginary pole.",
        "Attempt to walk on your elbows and knees for as far as you can.",
        "Choose someone from the group to give you a spanking.",
        "Post an extremely unflattering picture of yourself to the social media outlet of your choosing.\n",
        "Make a funny face and keep making it for 2 minutes while the game continues.",
        "Imagine something in your room. Now spell it with your nose and keep spelling it with your nose until someone from the group guesses what you are trying spell.",
        "After the group chooses one word, sing a song and insert that word once into every line of the song.",
        "Drag your butt on the carpet like a dog from one end of the room to the other.",
        "Open a bag of snacks or candy using only your mouth, no hands or feet.",
        "Bend at the waist so that you are looking behind you between your legs. Now run backwards until you can tag someone with your butt.",
        "Make a tea out of something that isn’t tea (but is NOT dangerous or toxic) and drink it.",
        "Go to the bathroom, take off your underwear and put it on your head. Wear it on your head for 10 minutes.",
        "Act like whatever animal someone yells out for the next 1 minute.",
        "Eat one teaspoon of the spiciest thing you have in the kitchen.",
        "Transfer an ice cube from the person on the right’s mouth to yours.",
        "Call the 7th contact in your phone and sing them 30 seconds of a song that the group chooses.",
        "No talking. Pretend to be a food. Don’t pretend to eat the food, pretend to be the food. Keep pretending until someone in the group guesses the food you are.",
        "Drop something in the toilet and then reach in to get it.",
        "Find the person whose first name has the same letter as your first name or whoever’s first name’s first letter is closest to yours. Now lick their face or let them lick your face, their choice.",
        "Sit in a spinning chair and have the group spin you for 30 seconds. Might help to hold a trash can just in case.",
        "Jump up and down as high as you can go for a full minute.",
        "Let two people give you a wet willy at the same time.",
        "Sing a praise song about a person of the groups choosing."
    )
    private var truth = arrayListOf(
        "What are your top three turn-ons?",
        "What is your deepest darkest fear?",
        "Tell me about your first kiss.",
        "Who is the sexiest person here?",
        "What is your biggest regret?",
        "Who here has the nicest butt?",
        "Who is your crush?",
        "Who was the last person you licked?",
        "Have you ever cheated or been cheated on?",
        "Tell me about your most awkward date.",
        "Have you ever made out with someone here?",
        "What are you most self-conscious about?",
        "When was the last time you peed in bed?",
        "What is the biggest lie you have ever told?",
        "What is the most embarrassing picture of you?",
        "Who is the person you most regret kissing?",
        "Tell us your most embarrassing vomit story.",
        "What is the naughtiest thing you’ve done in public?",
        "What do most people think is true about you, but isn’t?",
        "What is the biggest thing you’ve gotten away with?",
        "What would you do if you were the opposite sex for a month?",
        "What is the most expensive thing you have stolen?",
        "What is the most childish thing you still do?",
        "What’s the worst time you let someone take the blame for something you did?",
        "What is something your friends would never expect that you do?",
        "Who here would you most like to make out with?",
        "What lie have you told that hurt someone?",
        "What is the meanest you have been to someone that didn’t deserve it?",
        "What is something that people think you would never be into, but you are?",
        "What was the worst encounter you had with a police officer?",
        "What is the silliest thing you have an emotional attachment to?",
        "Where is the strangest place you have peed?",
        "Have you ever crapped your pants since you were a child?",
        "What is the most embarrassing thing your parents have caught you doing?",
        "What secret about yourself did you tell someone in confidence and then they told a lot of other people?",
        "When was the most inappropriate time you farted?",
        "What is the scariest dream you have ever had?",
        "What is the most embarrassing thing in your room?",
        "Why did you break up with your last boyfriend or girlfriend?",
        "What is the stupidest thing you have ever done?",
        "What is the grossest thing that has come out of your body?",
        "What is your favorite thing that your boyfriend or girlfriend does?",
        "What terrible thing have you done that you lied to cover up?",
        "Who have you loved but they didn’t love you back?",
        "What is something that you have never told anyone?",
        "What is the most disgusting habit you have?",
        "What pictures or videos of you do you wish didn’t exist?",
        "What was the cruelest joke you played on someone?",
        "What is the most embarrassing thing you have put up on social media?",
        "What horrible thing have you done that no one else found out about?",
        "What was the most awkward romantic encounter you have had?",
        "What is the grossest thing you have had in your mouth?",
        "Tell me about the last time someone unexpectedly walked in on you while you were naked.",
        "What is the most embarrassing nickname you have ever had?",
        "What is the most embarrassing series of texts you have on your phone?",
        "Describe your most recent romantic encounter in detail.",
        "What is the weirdest thing you have done for a boyfriend or girlfriend?",
        "Is it true that you (whatever you or the group suspects they do / did)?",
        "When was the last time you wiped a booger off on something that shouldn’t have boogers on it?",
        "What do you really hope your parents never find out about?",
        "Tell me something you don’t want me to know.",
        "What have you done that people here would judge you most for doing?",
        "If you starred in a romance, what would it be like?"
    )

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: MainFragmentBinding

    var players: List<Player> = arrayListOf<Player>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = PlayerDatabase.getInstance(application).playerDatabaseDao
        val dataSource2 = PlayerDatabase.getInstance(application).questionDatabaseDao()

        val viewModelFactory = MainViewModelFactory(dataSource, dataSource2)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        btnTruth = binding.btnTruth
        btnDare = binding.btnDare
        btnEnd = binding.btnEnd


        binding.apply {
            viewModel.deleteDatabase()
            viewModel.buildDatabase(
                listOf(
                    Question("Serenade the person to your right.", false),
                    Question("Talk in an accent for the next 3 rounds.", false),
                    Question("Kiss the person to your left.", false),
                    Question("Attempt to do a magic trick.", false),
                    Question("Do four cartwheels in row.", false),
                    Question("Let someone shave part of your body.", false),
                    Question("Eat five tablespoons of a condiment.", false),
                    Question("Be someone’s pet for the next 5 minutes.", false),
                    Question("Attempt to break dance for 30 seconds.", false),
                    Question("Let the group give you a new hairstyle.", false),
                    Question("Do the worm.", false),
                    Question("Curse like sailor for 20 seconds straight.", false),
                    Question("Sniff the armpits of everyone in the room.", false),
                    Question("Dance to a song of the group’s choosing.", false),
                    Question("Take a shower with your clothes on.", false),
                    Question("Break two eggs on your head.", false),
                    Question("Do your best impression of a baby being born.", false),
                    Question("Belly dance like your life depended on it.", false),
                    Question("Put 4 ice cubes down your pants.", false),
                    Question("Lick the floor.", false)


                )
            )
        }
        binding.apply {
            viewModel.buildDatabase(
                listOf(
                    Question("What is your deepest darkest fear?", true),
                    Question("Tell me about your first kiss.", true),
                    Question("What are your top three turn-ons?", true),
                    Question("Who is the sexiest person here?", true),
                    Question("What is your biggest regret?", true),
                    Question("Who here has the nicest butt?", true),
                    Question("Who is your crush?", true),
                    Question("Who was the last person you licked?", true),
                    Question("Have you ever cheated or been cheated on?", true),
                    Question("Tell me about your most awkward date.", true),
                    Question("Have you ever made out with someone here?", true),
                    Question("What are you most self-conscious about?", true),
                    Question("When was the last time you peed in bed?", true),
                    Question("What is the biggest lie you have ever told?", true),
                    Question("What is the most embarrassing picture of you?", true)
                )
            )
        }


        viewModel.truthes?.observe(viewLifecycleOwner, Observer {

                x ->
            truth = x as ArrayList<String>

            //println("truthes: " + x)

        })

        viewModel.dares?.observe(viewLifecycleOwner, Observer { x ->
            this.dares = x as ArrayList<String>
            //println("dares: " + x)

        })


        chosenPlayer = binding.chosenPlayer

        //chosenPlayer.text=intent.getStringExtra("chosenPlayerName");

        textView = binding.text

        binding.apply {
            btnDare.setOnClickListener { setDare(dares) }

            btnTruth.setOnClickListener { setTruth(truth) }

            btnEnd.setOnClickListener {

                goRank()
            }
        }

        viewModel.players.observe(viewLifecycleOwner, Observer {

                x ->
            players = x
            binding.chosenPlayer.text = setPlayer(players).name
        })

        return binding.root
    }

    private fun setDare(dares: ArrayList<String>) {
        val dareChoice: String = dares.random()
        Toast.makeText(context, "dare clicked", Toast.LENGTH_SHORT).show()
        textView.text = dareChoice

    }

    private fun setTruth(truthes: ArrayList<String>) {
        val truthChoice: String = truthes.random()
        Toast.makeText(context, "truth clicked", Toast.LENGTH_SHORT).show()
        textView.text = truthChoice

    }

    fun goRank() {
        val action = MainFragmentDirections.actionMainFragmentToRankFragment()
        view!!.findNavController().navigate(action)
    }

    private fun setPlayer(players: List<Player>): Player {
        val playerChoice: Player = players.random()
        Toast.makeText(
            context, playerChoice.name,
            Toast.LENGTH_SHORT
        ).show()
        return playerChoice
    }

}
