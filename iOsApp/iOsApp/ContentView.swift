//
//  ContentView.swift
//  iOsApp
//
//  Created by Dmitry Makarov on 21/11/2019.
//  Copyright © 2019 ostrovok. All rights reserved.
//

import SwiftUI
import multiplatform

class ActualSender : AnalyticsSender {
    
    private(set) var lastAnalytics: String = ""
    
    func sendAnalytics(name: String, parameters: [String : Any]) {
        let genericsString = GenericDataType<AnalyticsSender>(data: self).stringValue()
        let values = parameters.reduce("[") { $0 + "\($1.key) => \($1.value) " } + "]"
        lastAnalytics = "Sending analytics from \(genericsString): \(name) + \(values)"
    }
    
}

struct ContentView: View {
    var body: some View {
        let sender = ActualSender()
        let analytics = Analytics(sender: sender)
        
        analytics.prepareAnalytics(type: .userTrigger) {
            Parameters(timestamp: Int64(Date().timeIntervalSince1970), account: "test_account")
        }
        
        return Text(sender.lastAnalytics)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
