import { useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { 
  Users, 
  Calendar, 
  FileText, 
  Settings, 
  Stethoscope, 
  UserCheck,
  ClipboardList,
  Bell,
  Search,
  Plus,
  Activity,
  TrendingUp
} from "lucide-react";
import { Input } from "@/components/ui/input";

const Dashboard = () => {
  const [userType] = useState("medico"); 

  const stats = [
    {
      title: "Pacientes Ativos",
      value: "1,234",
      change: "+12%",
      icon: Users,
      color: "text-primary"
    },
    {
      title: "Consultas Hoje",
      value: "23",
      change: "+5%",
      icon: Calendar,
      color: "text-info"
    },
    {
      title: "Prontuários",
      value: "856",
      change: "+8%",
      icon: FileText,
      color: "text-success"
    },
    {
      title: "Taxa de Ocupação",
      value: "89%",
      change: "+3%",
      icon: Activity,
      color: "text-primary"
    }
  ];

  const recentActivities = [
    {
      id: 1,
      patient: "Maria Silva",
      action: "Consulta realizada",
      time: "2h atrás",
      status: "completed"
    },
    {
      id: 2,
      patient: "João Santos",
      action: "Exame agendado",
      time: "4h atrás",
      status: "scheduled"
    },
    {
      id: 3,
      patient: "Ana Costa",
      action: "Resultado disponível",
      time: "6h atrás",
      status: "result"
    }
  ];

  const quickActions = [
    {
      title: "Nova Consulta",
      description: "Agendar nova consulta",
      icon: Plus,
      action: "schedule"
    },
    {
      title: "Buscar Paciente",
      description: "Encontrar prontuário",
      icon: Search,
      action: "search"
    },
    {
      title: "Relatórios",
      description: "Ver relatórios médicos",
      icon: ClipboardList,
      action: "reports"
    },
    {
      title: "Configurações",
      description: "Ajustar preferências",
      icon: Settings,
      action: "settings"
    }
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-medical-light via-background to-medical-secondary">
      {/* Header */}
      <header className="bg-white/80 backdrop-blur-md border-b border-medical-secondary/30 sticky top-0 z-10">
        <div className="container mx-auto px-6 py-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-4">
              <div className="bg-primary rounded-full p-2">
                <Stethoscope className="h-6 w-6 text-primary-foreground" />
              </div>
              <div>
                <h1 className="text-2xl font-bold bg-gradient-to-r from-primary to-info bg-clip-text text-transparent">
                  AVICIA
                </h1>
                <p className="text-sm text-muted-foreground">
                  Dashboard {userType === "medico" ? "Médico" : "Principal"}
                </p>
              </div>
            </div>
            
            <div className="flex items-center space-x-4">
              <div className="relative">
                <Search className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                <Input 
                  placeholder="Buscar pacientes..." 
                  className="pl-10 w-64"
                />
              </div>
              <Button variant="ghost" size="icon" className="relative">
                <Bell className="h-5 w-5" />
                <Badge className="absolute -top-1 -right-1 h-5 w-5 flex items-center justify-center p-0 text-xs bg-destructive">
                  3
                </Badge>
              </Button>
              <div className="flex items-center space-x-2">
                <UserCheck className="h-5 w-5 text-primary" />
                <span className="font-medium">Dr. Exemplo</span>
              </div>
            </div>
          </div>
        </div>
      </header>

      <main className="container mx-auto px-6 py-8">
        {/* Stats Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          {stats.map((stat, index) => (
            <Card key={index} className="shadow-[var(--medical-glow)] border-medical-secondary/30 hover:shadow-lg transition-all duration-300">
              <CardContent className="p-6">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-medium text-muted-foreground">
                      {stat.title}
                    </p>
                    <p className="text-2xl font-bold text-foreground">
                      {stat.value}
                    </p>
                    <p className="text-xs text-success flex items-center mt-1">
                      <TrendingUp className="h-3 w-3 mr-1" />
                      {stat.change}
                    </p>
                  </div>
                  <div className={`${stat.color} bg-medical-light rounded-full p-3`}>
                    <stat.icon className="h-6 w-6" />
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Quick Actions */}
          <div className="lg:col-span-2">
            <Card className="shadow-[var(--medical-glow)] border-medical-secondary/30">
              <CardHeader>
                <CardTitle className="text-primary">Ações Rápidas</CardTitle>
                <CardDescription>
                  Acesso rápido às funcionalidades principais
                </CardDescription>
              </CardHeader>
              <CardContent>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  {quickActions.map((action, index) => (
                    <Button
                      key={index}
                      variant="outline"
                      className="h-auto p-4 justify-start border-medical-secondary/50 hover:border-primary hover:bg-medical-light transition-all duration-300"
                    >
                      <div className="flex items-center space-x-3">
                        <div className="bg-primary/10 rounded-full p-2">
                          <action.icon className="h-5 w-5 text-primary" />
                        </div>
                        <div className="text-left">
                          <p className="font-medium">{action.title}</p>
                          <p className="text-sm text-muted-foreground">
                            {action.description}
                          </p>
                        </div>
                      </div>
                    </Button>
                  ))}
                </div>
              </CardContent>
            </Card>
          </div>

          {/* Recent Activities */}
          <div>
            <Card className="shadow-[var(--medical-glow)] border-medical-secondary/30">
              <CardHeader>
                <CardTitle className="text-primary">Atividades Recentes</CardTitle>
                <CardDescription>
                  Últimas movimentações do sistema
                </CardDescription>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  {recentActivities.map((activity) => (
                    <div key={activity.id} className="flex items-start space-x-3">
                      <div className="bg-primary/10 rounded-full p-2 flex-shrink-0">
                        <FileText className="h-4 w-4 text-primary" />
                      </div>
                      <div className="flex-1 min-w-0">
                        <p className="text-sm font-medium text-foreground">
                          {activity.patient}
                        </p>
                        <p className="text-sm text-muted-foreground">
                          {activity.action}
                        </p>
                        <p className="text-xs text-muted-foreground">
                          {activity.time}
                        </p>
                      </div>
                      <Badge 
                        variant={activity.status === "completed" ? "default" : "secondary"}
                        className="flex-shrink-0"
                      >
                        {activity.status === "completed" ? "Concluído" : 
                         activity.status === "scheduled" ? "Agendado" : "Resultado"}
                      </Badge>
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Dashboard;