// src/pages/AdminDashboard/sections/DashboardSection.tsx
import React, { useState, useEffect } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Activity, Shield, CheckCircle, XCircle, Clock } from "lucide-react";
import { getStatusIcon } from "../utils/helpers";
import { SYSTEM_STATS } from "../utils/constants";

interface DashboardSectionProps {
  token: string | null;
  activeUserCount: number;
}

export const DashboardSection: React.FC<DashboardSectionProps> = ({
  token,
  activeUserCount,
}) => {
  const [recentActivities, setRecentActivities] = useState<any[]>([]);
  const [systemStatus, setSystemStatus] = useState<any[]>([]);

  useEffect(() => {
    if (token) {
      const savedActivities = localStorage.getItem("recentActivities");
      if (savedActivities) {
        setRecentActivities(JSON.parse(savedActivities));
      }
      setSystemStatus([]);
    }
  }, [token]);

  useEffect(() => {
    localStorage.setItem(
      "recentActivities",
      JSON.stringify(recentActivities.slice(-10))
    );
  }, [recentActivities]);

  // Update stats value
  const stats = [...SYSTEM_STATS];
  stats[0].value = activeUserCount;

  return (
    <div className="space-y-6">
      <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
        {stats.map((stat, index) => (
          <Card key={index} className="hover:shadow-md transition-shadow">
            <CardContent className="p-6">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-sm text-gray-500">{stat.label}</p>
                  <p className="text-3xl font-bold text-gray-800">
                    {stat.value}
                  </p>
                </div>
                <stat.icon className={`h-8 w-8 ${stat.color}`} />
              </div>
            </CardContent>
          </Card>
        ))}
      </section>
      <section className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div className="lg:col-span-2">
          <Card>
            <CardHeader>
              <CardTitle className="flex items-center gap-2 text-gray-700">
                <Activity className="h-5 w-5" /> Atividades Recentes do Sistema
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {recentActivities.length > 0 ? (
                  recentActivities.map((activity, index) => (
                    <div
                      key={index}
                      className="flex items-center justify-between p-3 rounded-lg bg-gray-100/50"
                    >
                      <div className="flex items-center gap-3">
                        {getStatusIcon(activity.status)}
                        <div>
                          <p className="font-medium text-gray-800">
                            {activity.action}
                          </p>
                          <p className="text-sm text-gray-500">
                            por {activity.user}
                          </p>
                        </div>
                      </div>
                      <Badge
                        variant="outline"
                        className="text-xs text-gray-500"
                      >
                        {activity.time}
                      </Badge>
                    </div>
                  ))
                ) : (
                  <p className="text-sm text-gray-500 text-center py-4">
                    Nenhuma atividade recente.
                  </p>
                )}
              </div>
            </CardContent>
          </Card>
        </div>
        <div>
          <Card>
            <CardHeader>
              <CardTitle className="flex items-center gap-2 text-gray-700">
                <Shield className="h-5 w-5" /> Status do Sistema
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-3">
              {systemStatus.length > 0 ? (
                systemStatus.map((item, index) => {
                  const icon =
                    item.status === "ok" ? (
                      <CheckCircle className="h-5 w-5 text-green-500" />
                    ) : item.status === "warning" ? (
                      <Clock className="h-5 w-5 text-yellow-500" />
                    ) : (
                      <XCircle className="h-5 w-5 text-red-500" />
                    );
                  const bgColor =
                    item.status === "ok"
                      ? "bg-green-50 border-green-200"
                      : item.status === "warning"
                      ? "bg-yellow-50 border-yellow-200"
                      : "bg-red-50 border-red-200";
                  return (
                    <div
                      key={index}
                      className={`flex items-center gap-2 p-3 rounded-lg border ${bgColor}`}
                    >
                      {icon}
                      <div>
                        <span className="font-medium text-gray-800">
                          {item.name}
                        </span>
                        <p className="text-sm text-gray-500">{item.message}</p>
                      </div>
                    </div>
                  );
                })
              ) : (
                <p className="text-sm text-gray-500 text-center py-4">
                  Nenhum status para exibir.
                </p>
              )}
            </CardContent>
          </Card>
        </div>
      </section>
    </div>
  );
};
