package net.luisg.pipe_puzzle_solver;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

	Spinner greenLightSpinner;
	Spinner purpleCylinderSpinner;

	//Views that include where to put solution
	TextView tankView;
	TextView barracksView;
	TextView deptView;
	TextView controlView;
	TextView armoryView;
	TextView supplyView;

	Button calcButton;

	int TANK = 0;
	int BARRACK = 1;
	int DEPT = 2;
	int CONTROL = 3;
	int ARMORY = 4;
	int SUPPLY = 5;

	int[] TANK_LINKS = 		{ BARRACK,	SUPPLY,		ARMORY};
	int[] BARRACK_LINKS =	{ DEPT,		TANK,		CONTROL};
	int[] DEPT_LINKS = 		{ ARMORY,		BARRACK,		CONTROL};
	int[] CONTROL_LINKS = 	{ SUPPLY,		DEPT,		BARRACK};
	int[] ARMORY_LINKS = 	{ SUPPLY,		TANK,		DEPT};
	int[] SUPPLY_LINKS =	{ CONTROL,	ARMORY,		TANK};

	int[][] links = {TANK_LINKS,BARRACK_LINKS,DEPT_LINKS,CONTROL_LINKS,ARMORY_LINKS,SUPPLY_LINKS};

	int[] solution = {0,0,0,0,0,0};

	int[] resultStrResources = {R.string.str_1,R.string.str_2,R.string.str_3,R.string.str_any};

	public int traverse_pipe(int current,int goal,int[] traversed)
	{
		//Check if we have already traversed this pipe
		if(traversed[current] != 0)
			return 0;
		traversed[current] = 1;
		//If we are at the goal
		if(current == goal)
		{
			//Check if we have traversed all of the pipes
			if(traversed[0] + traversed[1] + traversed[2] + traversed[3] + traversed[4] + traversed[5] == 6)
			{
				//We have found a solution!
				solution[current] = 3;//3 means any
				return 1;
			}
			else
			{
				traversed[current] = 0;
				return 0;
			}
		}
		//Traverse each of our 3 pipes
		for(int i = 0; i < 3; i++)
		{
			int result = traverse_pipe(links[current][i],goal,traversed);
			//Check if traversing pipe i yields a solution
			if(result == 1)
			{
				solution[current] = i;
				return 1;
			}
		}
		traversed[current] = 0;
		return 0;

	}

	public void calculateSolution()
	{
		if(greenLightSpinner == null || purpleCylinderSpinner == null)
			return;

		//TODO: solve the path
		int start = greenLightSpinner.getSelectedItemPosition();
		int goal = purpleCylinderSpinner.getSelectedItemPosition();

		if(start == goal)
		{
			Toast.makeText(this, "Error: the start and end locations cannot be the same", Toast.LENGTH_LONG).show();
			char[] none = {'-','-'};
			tankView.setText(none,0,2);
			barracksView.setText(none,0,2);
			deptView.setText(none,0,2);
			controlView.setText(none,0,2);
			armoryView.setText(none,0,2);
			supplyView.setText(none,0,2);
			return;
		}

		int[] traversed = {0,0,0,0,0,0};
		traverse_pipe(start,goal,traversed);

		tankView.setText(resultStrResources[solution[0]]);
		barracksView.setText(resultStrResources[solution[1]]);
		deptView.setText(resultStrResources[solution[2]]);
		controlView.setText(resultStrResources[solution[3]]);
		armoryView.setText(resultStrResources[solution[4]]);
		supplyView.setText(resultStrResources[solution[5]]);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		/*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
					   .setAction("Action", null).show();
			}
		});*/
		//==================================================================================
		greenLightSpinner = (Spinner) findViewById(R.id.green_light_spinner);

		ArrayAdapter<CharSequence> start_adapter =
			ArrayAdapter.createFromResource(this,R.array.pipe_locations,android.R.layout.simple_spinner_item);

		start_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		greenLightSpinner.setAdapter(start_adapter);
		//===================================================================================
		purpleCylinderSpinner = (Spinner) findViewById(R.id.purple_cylinder_spinner);

		ArrayAdapter<CharSequence> goal_adapter =
			ArrayAdapter.createFromResource(this,R.array.pipe_locations,android.R.layout.simple_spinner_item);
		goal_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		purpleCylinderSpinner.setAdapter(goal_adapter);
		//==================================================================================

		tankView = (TextView) findViewById(R.id.tank_num);
		barracksView = (TextView) findViewById(R.id.barracks_num);
		deptView = (TextView) findViewById(R.id.dept_num);
		controlView = (TextView) findViewById(R.id.control_num);
		armoryView = (TextView) findViewById(R.id.armory_num);
		supplyView = (TextView) findViewById(R.id.supply_num);

		calcButton = (Button) findViewById(R.id.calc_button);

		calcButton.setOnClickListener(
		new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//button clicked
				calculateSolution();
			}
		}
		);

	}
}
