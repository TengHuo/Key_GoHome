//
//  ViewController.swift
//  TrainHelper
//
//  Created by Teng on 4/10/16.
//  Copyright © 2016 huoteng. All rights reserved.
//

import UIKit

let APIKey = "5ae773cd45d72313c87b0f2dd3a0bddc"
class MapViewController: UIViewController, MAMapViewDelegate {
    
    var mapView:MAMapView?

    override func viewDidLoad() {
        super.viewDidLoad()
        
        MAMapServices.sharedServices().apiKey = APIKey
        
        initMapView()
    }
    
    func initMapView() {
        mapView = MAMapView(frame: self.view.bounds)
        mapView!.delegate = self
        self.view.addSubview(mapView!)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

