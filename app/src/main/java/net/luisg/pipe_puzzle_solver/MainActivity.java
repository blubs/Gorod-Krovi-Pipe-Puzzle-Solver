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


	public void calculateSolution()
	{
		if(greenLightSpinner == null || purpleCylinderSpinner == null)
			return;

		//TODO: solve the path

		int start = greenLightSpinner.getSelectedItemPosition();
		int goal = purpleCylinderSpinner.getSelectedItemPosition();

		Log.println(Log.INFO,"info","Going from: " + start + " to " + goal + ".");
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if(id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
